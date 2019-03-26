package com.lunny.xokhttp.request;

import java.util.HashMap;
import java.util.Map;

public final class Request {
    private String url;
    private Method method;
    private Map<String, String> headers;
    private RequestBody body;

    private Request() {
    }

    private Request(String url, Method method, RequestBody body) {
        this.url = url;
        this.method = method;
        this.body = body;
    }

    public String url() {
        return url;
    }

    public RequestBody body() {
        return body;
    }

    public String header(String key) {
        return headers.get(key);
    }

    public Builder newBuilder() {
        Builder builder = new Builder();
        builder.url = this.url;
        builder.method = this.method;
        builder.body = this.body;
        return builder;
    }

    public static class Builder {
        private String url;
        private Method method;
        private RequestBody body;
        private Map<String, String> headers;

        public Builder() {
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder method(Method method) {
            this.method = method;
            return this;
        }

        public Builder addHeader(String header, String value) {
            headers.put(header, value);
            return this;
        }

        public Builder removeHeader(String header) {
            headers.remove(header);
            return this;
        }

        public Request build() {
            return new Request(url, method, body);
        }
    }

}
