package com.wudaozi.exception;

import com.wudaozi.coder.AAAAA;

/**
 * Created by yunzhongtianjing on 16/2/28.
 */
@AAAAA
public class WudaoziException extends RuntimeException {
    public WudaoziException() {
    }

    public WudaoziException(String detailMessage, Object... params) {
        super(String.format(detailMessage, params));
    }


    public WudaoziException(Throwable throwable) {
        super(throwable);
    }
}
