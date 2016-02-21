package com.yunzhongtianjing.wudaozi.buffer;

import android.graphics.Bitmap;
import android.opengl.GLES20;

import com.yunzhongtianjing.wudaozi.common.ColorFormat;

import static android.opengl.GLES20.*;

/**
 * Created by yunhun.fyy on 2016/2/21.
 */
public abstract class RenderBuffer {
    private static RenderBuffer mCurrentRenderBuffer;

    private final int mHandle;

    private RenderBuffer(int handle) {
        this.mHandle = handle;
    }

    public void bind() {
        glBindRenderbuffer(GL_RENDERBUFFER, mHandle);
        mCurrentRenderBuffer = this;
    }

    public void unbind() {
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
        mCurrentRenderBuffer = null;
    }

    public static RenderBuffer getCurrentRenderBuffer() {
        return mCurrentRenderBuffer;
    }

    public static class ColorBuffer extends RenderBuffer {
        private ColorBuffer(int handle) {
            super(handle);
        }

        public static ColorBuffer create(ColorFormat type, int width, int height) {
            final int[] buffers = new int[1];
            glGenRenderbuffers(1, buffers, 0);
            glBindRenderbuffer(GL_RENDERBUFFER, buffers[0]);
            glRenderbufferStorage(GL_RENDERBUFFER, type.GLType, width, height);
            return new ColorBuffer(buffers[0]);
        }
    }

    public static class DepthBuffer extends RenderBuffer {
        private DepthBuffer(int handle) {
            super(handle);
        }

        public static DepthBuffer create(int width, int height) {
            final int[] buffers = new int[1];
            glGenRenderbuffers(1, buffers, 0);
            glBindRenderbuffer(GL_RENDERBUFFER, buffers[0]);
            glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT16, width, height);
            return new DepthBuffer(buffers[0]);
        }
    }

    public static class StencilBuffer extends RenderBuffer {
        private StencilBuffer(int handle) {
            super(handle);
        }

        public static StencilBuffer create(int width, int height) {
            final int[] buffers = new int[1];
            glGenRenderbuffers(1, buffers, 0);
            glBindRenderbuffer(GL_RENDERBUFFER, buffers[0]);
            //Since GL_STENCIL_INDEX is deprecated,we won't provide it again,only GL_STENCIL_INDEX8 provided
            glRenderbufferStorage(GL_RENDERBUFFER, GL_STENCIL_INDEX8, width, height);
            return new StencilBuffer(buffers[0]);
        }
    }
}
