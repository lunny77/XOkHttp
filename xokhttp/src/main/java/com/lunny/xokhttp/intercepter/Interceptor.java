package com.lunny.xokhttp.intercepter;

import com.lunny.xokhttp.Chain;
import com.lunny.xokhttp.Response;

public interface Interceptor {

    Response intercept(Chain chain);

}
