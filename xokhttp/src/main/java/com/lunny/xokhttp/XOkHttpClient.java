package com.lunny.xokhttp;

import com.lunny.xokhttp.cache.Cache;
import com.lunny.xokhttp.intercepter.Interceptor;
import com.lunny.xokhttp.request.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class XOkHttpClient {
    private List<Interceptor> interceptors;
    private List<Interceptor> networkInterceptors;
    private Dispatcher dispatcher;
    private Cache cache;
    private ExecutorService executorService;

    private XOkHttpClient() {
        this.dispatcher = new Dispatcher(this);
    }

    private XOkHttpClient(Builder builder) {
        this();
        interceptors = builder.interceptors;
        networkInterceptors = builder.networkInterceptors;
        cache = builder.cache;
        executorService = builder.executorService;
    }

    public Call newCall(Request request) {
        return RealCall.create(this, request);
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public Cache getCache() {
        return cache;
    }

    public ExecutorService getExecutorService(){
        return this.executorService;
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public List<Interceptor> getNetworkInterceptors() {
        return networkInterceptors;
    }

    public static class Builder {
        List<Interceptor> interceptors;
        List<Interceptor> networkInterceptors;
        Cache cache;
        ExecutorService executorService;

        public Builder() {
            this.interceptors = new ArrayList<>();
            this.networkInterceptors = new ArrayList<>();
        }

        public Builder addInterceptor(Interceptor interceptor) {
            this.interceptors.add(interceptor);
            return this;
        }

        public Builder addNetworkInterceptor(Interceptor interceptor) {
            this.networkInterceptors.add(interceptor);
            return this;
        }

        public Builder addCache(Cache cache) {
            this.cache = cache;
            return this;
        }

        public Builder addExecutorServices(ExecutorService executorService) {
            this.executorService = executorService;
            return this;
        }

        public XOkHttpClient build() {
            return new XOkHttpClient(this);
        }
    }
}
