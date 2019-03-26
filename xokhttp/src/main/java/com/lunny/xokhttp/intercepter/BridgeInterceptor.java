package com.lunny.xokhttp.intercepter;

import com.lunny.xokhttp.Chain;
import com.lunny.xokhttp.request.MediaType;
import com.lunny.xokhttp.request.Request;
import com.lunny.xokhttp.Response;
import com.lunny.xokhttp.request.RequestBody;
import com.lunny.xokhttp.utils.Util;

public class BridgeInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        //对请求处理
        RequestBody requestBody = request.body();
        MediaType mediaType = requestBody.contentType();
        if (mediaType != null) {
            requestBuilder.addHeader("Content-Type", mediaType.toString());
        }

        long contentLength = requestBody.contentLength();
        if (contentLength != -1L) {
            requestBuilder.addHeader("Content-Length", Long.toString(contentLength));
            requestBuilder.removeHeader("Transfer-Encoding");
        } else {
            requestBuilder.addHeader("Transfer-Encoding", "chunked");
            requestBuilder.removeHeader("Content-Length");
        }

        if (request.header("Host") == null) {
            requestBuilder.addHeader("Host", Util.hostHeader(request.url()));
        }
        if (request.header("Connection") == null) {
            requestBuilder.addHeader("Connection", "Keep-Alive");
        }
        if (request.header("User-Agent") == null) {
            requestBuilder.addHeader("User-Agent", Util.userAgent());
        }

        Response response = chain.proceed(request);
        //对响应进行处理

        return response;
    }

}
