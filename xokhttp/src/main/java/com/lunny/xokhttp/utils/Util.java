package com.lunny.xokhttp.utils;

import com.lunny.xokhttp.request.Request;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

public class Util {

    public static String hostHeader(String url) {
        return null;
    }

    public static String userAgent() {
        return "xokhttp/1.0";
    }

    public static String threadName(Request request) {
        return request.url();
    }

    public static ThreadFactory threadFactory(final String name, final boolean daemon) {
        return new ThreadFactory() {
            @Override
            public Thread newThread(@NotNull Runnable runnable) {
                Thread thread = new Thread(name);
                thread.setDaemon(daemon);
                return thread;
            }
        };
    }
}
