package com.lunny.xokhttp.request;

public final class RequestBody {
    private MediaType contentType;

    public MediaType contentType() {
        return contentType;
    }

    public long contentLength() {
        return 0;
    }

    public static class Builder {

    }
}
