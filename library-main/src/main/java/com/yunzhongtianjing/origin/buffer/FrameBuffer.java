package com.yunzhongtianjing.origin.buffer;

import com.yunzhongtianjing.origin.OpenGLObject;

import static android.opengl.GLES20.*;

/**
 * Created by yunhun.fyy on 2016/2/21.
 */
public class FrameBuffer extends OpenGLObject{


    private static FrameBuffer mCurrentFrameBuffer;

    @Override
    public int create() {
        final int[] handles = new int[1];
        glGenFramebuffers(1, handles, 0);
        return handles[0];
    }

    @Override
    public void destroy() {
        glDeleteFramebuffers(0,new int[]{mHandle},0);
    }

    public static FrameBuffer getCurrentFrameBuffer() {
        return mCurrentFrameBuffer;
    }

    @Override
    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, mHandle);
        mCurrentFrameBuffer = this;
    }

    @Override
    public void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        mCurrentFrameBuffer = null;
    }


}
