package com.lunny.xokhttp;

import com.lunny.xokhttp.intercepter.BridgeInterceptor;
import com.lunny.xokhttp.intercepter.CacheInterceptor;
import com.lunny.xokhttp.intercepter.CallServerInterceptor;
import com.lunny.xokhttp.intercepter.ConnectInterceptor;
import com.lunny.xokhttp.intercepter.Interceptor;
import com.lunny.xokhttp.intercepter.RetryAndFollowUpInterceptor;
import com.lunny.xokhttp.request.Request;
import com.lunny.xokhttp.utils.Util;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

public class RealCall implements Call {
    private XOkHttpClient client;
    private Request request;
    private boolean executed;

    private RealCall(XOkHttpClient client, Request request) {
        this.client = client;
        this.request = request;
    }

    public static RealCall create(XOkHttpClient client, Request request) {
        return new RealCall(client, request);
    }

    @Override
    public Request request() {
        return request;
    }

    @Override
    public Response execute() throws IOException {
        synchronized (this) {
            if (executed) throw new IllegalStateException("Already Executed!");
            executed = true;
        }
        Dispatcher dispatcher = client.getDispatcher();
        try {
            dispatcher.execute(this);
            return getResponseFromInterceptors();
        } finally {
            dispatcher.finish(this);
        }
    }

    @Override
    public void enqueue(Callback callback) {
        synchronized (this) {
            if (executed) throw new IllegalStateException("Already Executed!");
            executed = true;
        }
        client.getDispatcher().enqueue(new AsyncCall(client, this, callback));
    }

    final static class AsyncCall extends NamedRunnable {
        XOkHttpClient client;
        Call call;
        Callback callback;

        public AsyncCall(XOkHttpClient client, Call call, Callback callback) {
            super(Util.threadName(call.request()));
            this.client = client;
            this.call = call;
            this.callback = callback;
        }

        void onExecute(ExecutorService executorService) {
            boolean success = false;
            try {
                executorService.execute(this);
                success = true;
            } catch (RejectedExecutionException e) {
                InterruptedIOException ioException = new InterruptedIOException("Executor reject!");
                callback.onFailed(call, ioException);
            } finally {
                if (!success) {
                    client.getDispatcher().finish(this);
                }
            }
        }

        @Override
        void execute() {
            try {
                Response response = ((RealCall) call).getResponseFromInterceptors();
                if (callback != null) {
                    callback.onResponse(call, response);
                }
            } catch (IOException e) {
                if (callback != null) {
                    callback.onFailed(call, e);
                }
            } finally {
                client.getDispatcher().finish(call);
            }
        }
    }

    private Response getResponseFromInterceptors() throws IOException {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.addAll(client.getInterceptors());
        interceptors.add(new RetryAndFollowUpInterceptor());
        interceptors.add(new BridgeInterceptor());
        interceptors.add(new CacheInterceptor(client.getCache()));
        interceptors.add(new ConnectInterceptor());
        interceptors.addAll(client.getNetworkInterceptors());
        interceptors.add(new CallServerInterceptor());
        Chain chain = new RealInterceptChain(client, interceptors, request, this);
        Response response = chain.proceed(request);
        return response;
    }
}
