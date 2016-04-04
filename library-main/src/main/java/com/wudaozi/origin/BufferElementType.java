package com.wudaozi.origin;

import android.opengl.GLES20;

import com.wudaozi.exception.WudaoziException;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public enum BufferElementType {
    SHORT(BufferElementType.BYTES_PER_SHORT, GLES20.GL_SHORT),
    INT(BufferElementType.BYTES_PER_INT, GLES20.GL_FIXED),
    FLOAT(BufferElementType.BYTES_PER_FLOAT, GLES20.GL_FLOAT),
    BYTE(BufferElementType.BYTES_PER_BYTE, GLES20.GL_BYTE);

    private static final int BYTES_PER_SHORT = Short.SIZE / Byte.SIZE;
    private static final int BYTES_PER_INT = Integer.SIZE / Byte.SIZE;
    private static final int BYTES_PER_FLOAT = Float.SIZE / Byte.SIZE;
    private static final int BYTES_PER_BYTE = Byte.SIZE / Byte.SIZE;
    public final int byteSize;
    public final int glType;

    BufferElementType(int byteSize, int glType) {
        this.byteSize = byteSize;
        this.glType = glType;
    }
}