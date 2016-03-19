package com.wudaozi.exception;


/**
 * Created by yunzhongtianjing on 16/2/28.
 */
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
