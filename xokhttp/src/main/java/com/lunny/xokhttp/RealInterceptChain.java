package com.lunny.xokhttp;

import com.lunny.xokhttp.intercepter.Interceptor;
import com.lunny.xokhttp.request.Request;

import java.util.List;

public class RealInterceptChain implements Chain {
    private XOkHttpClient client;
    private List<Interceptor> interceptors;
    private Request request;
    private Call call;
    private int index;

    public RealInterceptChain( XOkHttpClient client, List<Interceptor> interceptors, Request request, Call call) {
        this.client = client;
        this.interceptors = interceptors;
        this.request = request;
        this.call = call;
    }

   @Override
    public Request request() {
        return request;
    }
    @Override
    public Call call() {
        return call;
    }

    @Override
    public Response proceed(Request request) {
        Interceptor interceptor = interceptors.get(index++);
        Response response = interceptor.intercept(this);
        return response;
    }
}
