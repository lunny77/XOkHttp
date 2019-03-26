package com.lunny.xokhttp.request;

public class MediaType {
    private String type;

    private MediaType(String type) {
        this.type = type;
    }

    public static MediaType crate(String type) {
        return new MediaType(type);
    }

    @Override
    public String toString() {
        return type;
    }
}
