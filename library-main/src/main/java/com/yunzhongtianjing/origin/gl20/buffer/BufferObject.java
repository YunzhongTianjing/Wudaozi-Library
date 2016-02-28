package com.yunzhongtianjing.origin.gl20.buffer;


import com.yunzhongtianjing.exception.WudaoziException;
import com.yunzhongtianjing.origin.OpenGLObject;
import com.yunzhongtianjing.origin.common.Utils;
import com.yunzhongtianjing.origin.gl20.DeviceSupport;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static android.opengl.GLES20.*;

/**
 * Created by yunzhongtianjing on 16/2/27.
 */
public class BufferObject extends OpenGLObject {
    private static final int BYTES_PER_SHORT = Short.SIZE / Byte.SIZE;
    private static final int BYTES_PER_FLOAT = Float.SIZE / Byte.SIZE;
    private static final int BYTES_PER_INT = Integer.SIZE / Byte.SIZE;

    protected final int mBoundPoint;

    private BufferObject(int boundPoint) {
        super();
        this.mBoundPoint = boundPoint;
    }


    @Override
    protected int generate() {
        final int[] buffers = new int[1];
        glGenBuffers(0, buffers, 0);
        return buffers[0];
    }

    @Override
    public void delete() {
        glDeleteBuffers(0, new int[]{mHandle}, 0);
    }

    @Override
    public void bind() {
        glBindBuffer(mBoundPoint, mHandle);
    }

    @Override
    public void unbind() {
        glBindBuffer(mBoundPoint, 0);
    }

    void setData(Buffer data, int byteSize, int usage) {
        bind();
        glBufferData(mBoundPoint, data.flip().limit() * byteSize, data, usage);
        unbind();
    }


    public static class ArrayBufferObject extends BufferObject {
        public ArrayBufferObject() {
            super(GL_ARRAY_BUFFER);
        }

        /**
         * @param usage Optimisation hint for OpenGL,not so reliable
         */
        public void setData(FloatBuffer data, int usage) {
            setData(data, BYTES_PER_FLOAT, usage);
        }

        public void setData(FloatBuffer data) {
            setData(data, GL_STATIC_DRAW);
        }

    }

    public static class IndexBufferObject extends BufferObject {
        public IndexBufferObject() {
            super(GL_ELEMENT_ARRAY_BUFFER);
        }

        /**
         * @param usage Optimisation hint for OpenGL,not so reliable
         */
        public void setData(ShortBuffer data, int usage) {
            setData(data, BYTES_PER_SHORT, usage);
        }

        public void setData(ShortBuffer data) {
            setData(data, GL_STATIC_DRAW);
        }

        /**
         * @param usage Optimisation hint for OpenGL,not so reliable
         */
        public void setData(IntBuffer data, int usage) {
            if (!DeviceSupport.getInstance().supportIntIBO)
                throw new WudaoziException("Integer Buffer for IBO is not supported with this device");
            setData(data, BYTES_PER_INT, usage);
        }

        public void setData(IntBuffer data) {
            setData(data, GL_STATIC_DRAW);
        }
    }

}
