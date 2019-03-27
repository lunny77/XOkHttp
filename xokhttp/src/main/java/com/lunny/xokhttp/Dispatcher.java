package com.lunny.xokhttp;

import com.lunny.xokhttp.utils.Util;

import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Dispatcher {
    private XOkHttpClient client;
    private List<Call> runningSyncCalls;
    private List<RealCall.AsyncCall> runningAsyncCalls;
    private ExecutorService executorService;

    public Dispatcher(XOkHttpClient client) {
        this.client = client;
        this.runningSyncCalls = new ArrayList<>();
        this.runningAsyncCalls = new ArrayList<>();
        executorService = client.getExecutorService();
    }

    private ExecutorService executorService() {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(), Util.threadFactory("XOkHttp Dispatcher", false));
        }
        return executorService;
    }

    public void execute(Call call) {
        runningSyncCalls.add(call);
    }

    public void enqueue(RealCall.AsyncCall asyncCall) {
        ExecutorService executorService = executorService();
        asyncCall.onExecute(executorService);
    }

    public void finish(Call call) {
        synchronized (this) {
            runningSyncCalls.remove(call);
        }
    }

    public void finish(RealCall.AsyncCall call) {
        synchronized (this) {
            runningAsyncCalls.remove(call);
        }
    }
}
