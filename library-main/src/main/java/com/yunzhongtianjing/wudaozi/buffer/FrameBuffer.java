package com.yunzhongtianjing.wudaozi.buffer;

import android.opengl.GLES20;

import static android.opengl.GLES20.*;

/**
 * Created by yunhun.fyy on 2016/2/21.
 */
public class FrameBuffer {
    private FrameBuffer(int handle) {
        this.mHandle = handle;
    }

    private final int mHandle;

    private static FrameBuffer mCurrentFrameBuffer;

    public static FrameBuffer create() {
        return create(1)[0];
    }

    public static FrameBuffer[] create(final int size) {
        final int[] handles = new int[size];
        glGenFramebuffers(size, handles, 0);
        final FrameBuffer[] buffers = new FrameBuffer[size];
        for (int i = 0; i < size; i++)
            buffers[i] = new FrameBuffer(handles[i]);
        return buffers;
    }

    public static FrameBuffer getCurrentFrameBuffer() {
        return mCurrentFrameBuffer;
    }

    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, mHandle);
        mCurrentFrameBuffer = this;
    }

    public void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        mCurrentFrameBuffer = null;
    }


}
