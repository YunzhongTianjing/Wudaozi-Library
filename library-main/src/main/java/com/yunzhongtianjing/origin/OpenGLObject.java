package com.yunzhongtianjing.origin;

/**
 * Created by yunzhongtianjing on 16/2/23.
 */
public abstract class OpenGLObject {
    protected final int mHandle;
    public OpenGLObject(){
        mHandle = generate();
    }
    protected abstract int generate();
    public abstract void delete();
    public abstract void bind();
    public abstract void unbind();
}
