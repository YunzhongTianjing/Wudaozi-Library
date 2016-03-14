package com.yunzhongtianjing.origin.gl20.program;

import com.yunzhongtianjing.exception.WudaoziException;

import static android.opengl.GLES20.*;

/**
 * Created by yunzhongtianjing on 16/3/14.
 */
class Shader {
    private final int mType;
    private String mSourceCode;
    private int mHandle;

    Shader(int mType, String sourceCode) {
        this.mType = mType;
        this.mSourceCode = sourceCode;
    }

    int getHandle() {
        if (0 == mHandle) {
            this.mHandle = loadShader();
            this.mSourceCode = null;
        }
        return mHandle;
    }

    private final int[] mReturnValues = new int[1];

    private int loadShader() {
        final int handle = glCreateShader(mType);
        if (handle == 0)
            throw new WudaoziException(String.format("%s create fail", this.getClass().getName()));
        glShaderSource(handle, mSourceCode);
        mSourceCode = null;
        glCompileShader(handle);
        if (!isCompiled()) {
            final String log = glGetShaderInfoLog(handle);
            glDeleteShader(handle);
            throw new WudaoziException(String.format("%s compile error-%s", this.getClass().getName(), log));
        }
        return handle;
    }

    boolean isCompiled() {
        glGetShaderiv(mHandle, GL_COMPILE_STATUS, mReturnValues, 0);
        return mReturnValues[0] == GL_TRUE;
    }

    boolean isDeleted() {
        glGetShaderiv(mHandle, GL_DELETE_STATUS, mReturnValues, 0);
        return mReturnValues[0] == GL_TRUE;
    }

    static class VertexShader extends Shader {
        VertexShader(String sourceCode) {
            super(GL_VERTEX_SHADER, sourceCode);
        }
    }

    static class FragmentShader extends Shader {
        FragmentShader(String sourceCode) {
            super(GL_FRAGMENT_SHADER, sourceCode);
        }
    }
}
