package com.lunny.xokhttp;

import java.io.IOException;

public interface Callback {

    void onResponse(Call call, Response response) throws IOException;

    void onFailed(Call call, IOException ex);

}
