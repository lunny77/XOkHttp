package com.lunny.xokhttp.intercepter;

import com.lunny.xokhttp.Chain;
import com.lunny.xokhttp.Response;
import com.lunny.xokhttp.cache.Cache;
import com.lunny.xokhttp.request.Request;

public class CacheInterceptor implements Interceptor {
    private Cache cache;

    public CacheInterceptor(Cache cache) {
        this.cache = cache;
    }

    @Override
    public Response intercept(Chain chain) {
        Request request = chain.request();
        Response responseCandidate = cache.get(request);
        if (cacheValid(responseCandidate)) {
            return responseCandidate;
        }
        Response response = chain.proceed(request);
        return response;
    }

    private boolean cacheValid(Response response) {
        return false;
    }

}
