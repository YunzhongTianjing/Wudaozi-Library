package com.yunzhongtianjing.origin.gl20.buffer;


import com.yunzhongtianjing.exception.WudaoziException;
import com.yunzhongtianjing.log.WLog;
import com.yunzhongtianjing.origin.OpenGLObject;
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
    private enum BufferElementType {
        SHORT(BufferElementType.BYTES_PER_SHORT),
        INT(BufferElementType.BYTES_PER_INT),
        FLOAT(BufferElementType.BYTES_PER_FLOAT);

        private static final int BYTES_PER_SHORT = Short.SIZE / Byte.SIZE;
        private static final int BYTES_PER_INT = Integer.SIZE / Byte.SIZE;
        private static final int BYTES_PER_FLOAT = Float.SIZE / Byte.SIZE;
        final int byteSize;

        BufferElementType(int byteSize) {
            this.byteSize = byteSize;
        }

        static BufferElementType getByBuffer(Buffer buffer) {
            if (buffer instanceof IntBuffer) {
                return INT;
            } else if (buffer instanceof ShortBuffer) {
                return SHORT;
            } else if (buffer instanceof FloatBuffer) {
                return FLOAT;
            } else {
                throw new WudaoziException(String.format("Parameter buffer{%s} is not valid", buffer));
            }
        }

    }

    private enum Usage {
        /**
         * The data store contents will be specified once by the application,
         * and used many times as the source for GL drawing commands
         */
        STATIC_DRAW(GL_STATIC_DRAW),
        /**
         * The data store contents will be respecified repeatedly by the application,
         * and used many times as the source for GL drawing commands
         */
        DYNAMIC_DRAW(GL_DYNAMIC_DRAW),
        /**
         * The data store contents will be specified once by the application,
         * and used at most a few times as the source of a GL drawing command.
         */
        STREAM_DRAW(GL_STREAM_DRAW);
        private final int glValue;

        Usage(int glValue) {
            this.glValue = glValue;
        }
    }

    protected final int mBoundPoint;

    private BufferElementType mElementType;
    private Usage mUsage;

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

    void superSetData(Buffer data, Usage usage) {
        this.mElementType = BufferElementType.getByBuffer(data);
        this.mUsage = usage;
        bind();
        glBufferData(mBoundPoint, data.flip().limit() * mElementType.byteSize, data, usage.glValue);
        unbind();
    }

    void superModifyData(Buffer newData, int start, int end) {
        final BufferElementType newElementType = BufferElementType.getByBuffer(newData);
        if (newElementType != mElementType)
            throw new WudaoziException(String.format("New data type{%s} mismatches origin data type{%s}!",
                    newElementType.name(), mElementType.name()));

        if (Usage.STATIC_DRAW == mUsage || Usage.STREAM_DRAW == mUsage)
            WLog.w("The buffer is used for %s,for efficiency,its content should be specified only once and shouldn't be modified",
                    mUsage.name());
        start = start * mElementType.byteSize;
        end = end * mElementType.byteSize;
        bind();
        glBufferSubData(mBoundPoint, start, end, newData);
        unbind();
    }


    public static class ArrayBufferObject extends BufferObject {
        public ArrayBufferObject() {
            super(GL_ARRAY_BUFFER);
        }

        /**
         * @param usage Optimisation hint for OpenGL(may not so reliable),see
         *              {@link com.yunzhongtianjing.origin.gl20.buffer.BufferObject.Usage}
         */
        public void setData(FloatBuffer data, Usage usage) {
            superSetData(data, usage);
        }

        public void setData(FloatBuffer data) {
            setData(data, Usage.STATIC_DRAW);
        }

        public void modifyData(FloatBuffer newData, int start, int end) {
            superModifyData(newData, start, end);
        }
    }

    public static class IndexBufferObject extends BufferObject {
        public IndexBufferObject() {
            super(GL_ELEMENT_ARRAY_BUFFER);
        }

        /**
         * @param usage Optimisation hint for OpenGL(may not so reliable),see
         *              {@link com.yunzhongtianjing.origin.gl20.buffer.BufferObject.Usage}
         */
        public void setData(ShortBuffer data, Usage usage) {
            superSetData(data, usage);
        }

        public void setData(ShortBuffer data) {
            setData(data, Usage.STATIC_DRAW);
        }

        public void modifyData(ShortBuffer newData, int start, int end) {
            superModifyData(newData, start, end);
        }


        /**
         * @param usage Optimisation hint for OpenGL(may not so reliable),see
         *              {@link com.yunzhongtianjing.origin.gl20.buffer.BufferObject.Usage}Ï
         */
        public void setData(IntBuffer data, Usage usage) {
            if (!isSupportIntegerBuffer())
                throw new WudaoziException("Integer Buffer for IBO is not supported with this device");
            superSetData(data, usage);
        }

        public void setData(IntBuffer data) {
            setData(data, Usage.STATIC_DRAW);
        }

        public void modifyData(IntBuffer newData, int start, int end) {
            superModifyData(newData, start, end);
        }

        public static boolean isSupportIntegerBuffer() {
            return DeviceSupport.getInstance().supportIntIBO;
        }
    }

}
