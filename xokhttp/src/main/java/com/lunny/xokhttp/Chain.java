package com.lunny.xokhttp;

import com.lunny.xokhttp.request.Request;

public interface Chain {

    Request request();

    Call call();

    Response proceed(Request request);

}
