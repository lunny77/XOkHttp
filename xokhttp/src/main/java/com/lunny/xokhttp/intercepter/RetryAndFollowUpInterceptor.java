package com.lunny.xokhttp.intercepter;

import com.lunny.xokhttp.Chain;
import com.lunny.xokhttp.request.Request;
import com.lunny.xokhttp.Response;

public class RetryAndFollowUpInterceptor implements Interceptor {
    private final int MAX_FOLLOW_UP_COUNT = 20;
    private int followUpCount;

    @Override
    public Response intercept(Chain chain) {
        Request request = chain.request();

        while (true) {
            Response response = null;
            try {
                response = chain.proceed(request);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }

            Request followUpRequest = followUpRequest(response);
            if (followUpRequest == null) {
                return response;
            }

            if (followUpCount++ > MAX_FOLLOW_UP_COUNT) {
                throw new IllegalStateException("To many follow-up requests: " + followUpCount);
            }
        }
    }

    private Request followUpRequest(Response response) {
        return null;
    }
}
