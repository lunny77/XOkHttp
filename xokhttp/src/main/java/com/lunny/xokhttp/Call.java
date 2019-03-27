package com.lunny.xokhttp;

import com.lunny.xokhttp.request.Request;

import java.io.IOException;

public interface Call {

    Request request();

    Response execute() throws IOException;

    void enqueue(Callback callback);

}
