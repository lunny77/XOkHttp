package com.lunny.xokhttp;

import com.lunny.xokhttp.request.Request;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;

public interface Chain {

    Request request();

    Call call();

    Response proceed(Request request) throws IOException;

}
