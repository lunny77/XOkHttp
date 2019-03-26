package com.lunny.xokhttp;

import com.lunny.xokhttp.cache.Cache;
import com.lunny.xokhttp.intercepter.Interceptor;

import java.util.ArrayList;
import java.util.List;

public class XOkHttpClient {
    private List<Interceptor> interceptors;
    private List<Interceptor> networkInterceptors;
    private Cache cache;

    private XOkHttpClient() {
    }

    private XOkHttpClient(Builder builder) {
        interceptors = builder.interceptors;
        networkInterceptors = builder.networkInterceptors;
        cache = builder.cache;
    }

    public static class Builder {
        List<Interceptor> interceptors;
        List<Interceptor> networkInterceptors;
        Cache cache;

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

        public XOkHttpClient build() {
            return new XOkHttpClient(this);
        }
    }
}
