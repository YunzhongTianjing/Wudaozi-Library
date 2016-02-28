package com.yunzhongtianjing.exception;

/**
 * Created by yunzhongtianjing on 16/2/28.
 */
public class WudaoziException extends RuntimeException{
    public WudaoziException() {
    }

    public WudaoziException(String detailMessage) {
        super(detailMessage);
    }

    public WudaoziException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public WudaoziException(Throwable throwable) {
        super(throwable);
    }
}
