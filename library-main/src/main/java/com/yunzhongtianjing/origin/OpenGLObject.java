package com.yunzhongtianjing.origin;

/**
 * Created by yunzhongtianjing on 16/2/23.
 */
public abstract class OpenGLObject {
    protected final int mHandle;
    public OpenGLObject(){
        mHandle = create();
    }
    protected abstract int create();
    public abstract void destroy();
    public abstract void bind();
    public abstract void unbind();
}
