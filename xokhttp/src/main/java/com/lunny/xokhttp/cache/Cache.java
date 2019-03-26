package com.lunny.xokhttp.cache;

import com.lunny.xokhttp.Response;
import com.lunny.xokhttp.request.Request;

public interface Cache {

    Response get(Request request);

}
