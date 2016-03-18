package com.wudaozi.origin.gl20.program;

import com.wudaozi.exception.WudaoziException;

import static android.opengl.GLES20.*;

/**
 * Created by yunhun.fyy on 2016/3/18.
 */
public abstract class Uniform {
    public final String name;
    final int mHandle;
    /**
     * If the uniform variable being queried is an array, this variable
     * will be written with the maximum array element used in the
     * program (plus 1). If the uniform variable being queried is not
     * an array, this value will be 1
     */
    final int mSizeIfArray;
    final int mComponentSize;

    final void ensureLegality(int valueArraySize) {
        if (0 != valueArraySize % mComponentSize || valueArraySize / mComponentSize != mSizeIfArray)
            throw new WudaoziException("Array size{%s} doesn't match {%s}!", valueArraySize / mComponentSize, mSizeIfArray);
    }

    private Uniform(String name, int handle, int sizeIfArray, int componentSize) {
        this.name = name;
        this.mHandle = handle;
        this.mSizeIfArray = sizeIfArray;
        this.mComponentSize = componentSize;
    }

    static Uniform create(String name, int handle, int sizeIfArray, int type) {
        switch (type) {
            case GL_FLOAT:
                return null;
            case GL_FLOAT_VEC2:
                return null;
            case GL_FLOAT_VEC3:
                return null;
            case GL_FLOAT_VEC4:
                return null;
            case GL_INT:
                return null;
            case GL_INT_VEC2:
                return null;
            case GL_INT_VEC3:
                return null;
            case GL_INT_VEC4:
                return null;
            case GL_BOOL:
                return null;
            case GL_BOOL_VEC2:
                return null;
            case GL_BOOL_VEC3:
                return null;
            case GL_BOOL_VEC4:
                return null;
            case GL_FLOAT_MAT2:
                return null;
            case GL_FLOAT_MAT3:
                return null;
            case GL_FLOAT_MAT4:
                return null;
            case GL_SAMPLER_2D:
                return null;
            case GL_SAMPLER_CUBE:
                return null;
            default:
                throw new WudaoziException("Unknown uniform type{%s}", type);
        }
    }
}
