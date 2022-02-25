package com.yunqi.starter.common.lang.stream;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by @author JsckChin on 2022/2/25
 */
public class VoidInputStream extends InputStream {

    @Override
    public int read() throws IOException {
        return -1;
    }
}
