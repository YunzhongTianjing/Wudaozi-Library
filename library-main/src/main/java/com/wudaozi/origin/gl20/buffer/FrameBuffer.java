package com.wudaozi.origin.gl20.buffer;

import com.wudaozi.origin.OpenGLObject;

import static android.opengl.GLES20.*;

/**
 * Created by yunhun.fyy on 2016/2/21.
 */
public class FrameBuffer extends OpenGLObject {


    private static FrameBuffer mCurrentFrameBuffer;

    @Override
    protected int generate(Object... params) {
        glGenFramebuffers(1, returnValues, 0);
        return returnValues[0];
    }

    @Override
    public void delete() {
        glDeleteFramebuffers(0, new int[]{handle}, 0);
    }

    public static FrameBuffer getCurrentFrameBuffer() {
        return mCurrentFrameBuffer;
    }

    @Override
    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, handle);
        mCurrentFrameBuffer = this;
    }

    @Override
    public void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        mCurrentFrameBuffer = null;
    }


}
