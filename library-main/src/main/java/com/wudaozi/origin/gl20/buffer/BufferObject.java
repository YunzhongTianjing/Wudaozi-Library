package com.wudaozi.origin.gl20.buffer;


import com.wudaozi.exception.WudaoziException;
import com.wudaozi.log.WLog;
import com.wudaozi.origin.BufferElementType;
import com.wudaozi.origin.OpenGLObject;
import com.wudaozi.origin.gl20.DeviceSupport;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static android.opengl.GLES20.*;

/**
 * Created by yunzhongtianjing on 16/2/27.
 */
public abstract class BufferObject extends OpenGLObject {
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

    private final int mBoundPoint;

    public final BufferElementType elementType;
    public final Usage usage;

    /**
     * @param usage Optimisation hint for OpenGL(may not so reliable),see
     *              {@link com.wudaozi.origin.gl20.buffer.BufferObject.Usage}Ï
     */
    protected BufferObject(int boundPoint, Buffer source, BufferElementType elementType, Usage usage) {
        super(boundPoint, source, elementType, usage);
        this.mBoundPoint = boundPoint;
        this.elementType = elementType;
        this.usage = usage;
    }

    private final int[] returnValues = new int[1];

    @Override
    protected int generate(Object... params) {
        final int boundPoint = (Integer) params[0];
        final Buffer source = (Buffer) params[1];
        final BufferElementType elementType = (BufferElementType) params[2];
        final Usage usage = (Usage) params[3];

        glGenBuffers(0, returnValues, 0);
        final int handle = returnValues[0];
        glBindBuffer(boundPoint, handle);
        glBufferData(boundPoint, source.flip().limit() * this.elementType.byteSize, source, usage.glValue);
        glBindBuffer(boundPoint, 0);

        return handle;
    }

    @Override
    public void delete() {
        glDeleteBuffers(0, new int[]{handle}, 0);
    }

    @Override
    public void bind() {
        glBindBuffer(mBoundPoint, handle);
    }

    @Override
    public void unbind() {
        glBindBuffer(mBoundPoint, 0);
    }


    public void modifyData(Buffer newData, int start, int end) {
        if (Usage.STATIC_DRAW == usage || Usage.STREAM_DRAW == usage)
            WLog.w("The buffer is used for %s,for efficiency,its content should be specified only once and shouldn't be modified",
                    usage.name());
        start = start * elementType.byteSize;
        end = end * elementType.byteSize;
        bind();
        glBufferSubData(mBoundPoint, start, end, newData);
        unbind();
    }


    public static class ArrayBufferObject extends BufferObject {
        public ArrayBufferObject(Buffer data, BufferElementType dataElementType, Usage usage) {
            super(GL_ARRAY_BUFFER, data, dataElementType, usage);
        }
    }

    public static class IndexBufferObject extends BufferObject {
        public IndexBufferObject(Buffer data, BufferElementType dataElementType, Usage usage) {
            super(GL_ELEMENT_ARRAY_BUFFER, data, dataElementType, usage);
            if (!isSupportIntegerBuffer() && BufferElementType.INT == dataElementType)
                throw new WudaoziException("IBO can't support Integer");
        }

        public static boolean isSupportIntegerBuffer() {
            return DeviceSupport.getInstance().supportIntIBO;
        }
    }

}
