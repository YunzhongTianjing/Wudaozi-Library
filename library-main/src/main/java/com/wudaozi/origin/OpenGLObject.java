package com.wudaozi.origin;

/**
 * Created by yunzhongtianjing on 16/2/23.
 */
public abstract class OpenGLObject {
    protected final int[] returnValues = new int[1];

    public final int handle;

    public OpenGLObject(Object... params) {
        handle = generate(params);
    }

    protected abstract int generate(Object... params);

    public abstract void delete();

    public abstract void bind();

    public abstract void unbind();
}
