package com.yunzhongtianjing.origin.gl20.program;

import com.yunzhongtianjing.exception.WudaoziException;

import static android.opengl.GLES20.*;

/**
 * Created by yunzhongtianjing on 16/3/14.
 */
public class Program {
    private final Shader.VertexShader mVertexShader;
    private final Shader.FragmentShader mFragmentShader;

    private int mHandle;

    public Program(String vertexShaderCode, String fragmentShaderCode) {
        this.mVertexShader = new Shader.VertexShader(vertexShaderCode);
        this.mFragmentShader = new Shader.FragmentShader(fragmentShaderCode);
    }

    public void initialize() {
        mHandle = getHandle();
    }

    public void use() {
        //TODO useDataSource().withFormat()
        glUseProgram(mHandle);
    }

    private int getHandle() {
        final int handle = glCreateProgram();
        if (handle == 0) throw new WudaoziException("Program create fail");
        glAttachShader(handle, mVertexShader.getHandle());
        glAttachShader(handle, mFragmentShader.getHandle());
        glLinkProgram(handle);
        if (!isLinked()) {
            final String log = glGetProgramInfoLog(handle);
            glDeleteProgram(handle);
            throw new WudaoziException(String.format("Program link error-%s", log));
        }
        return handle;
    }

    boolean isLinked() {
        return getProgramParam(GL_ACTIVE_ATTRIBUTES) == GL_TRUE;
    }

    boolean isValid() {
        return getProgramParam(GL_VALIDATE_STATUS) == GL_TRUE;
    }

    boolean isDeleted() {
        return getProgramParam(GL_DELETE_STATUS) == GL_TRUE;
    }

    private int getUniformValueSize() {
        return getProgramParam(GL_ACTIVE_UNIFORMS);
    }

    private int getAttributeValueSize() {
        return getProgramParam(GL_ACTIVE_ATTRIBUTES);
    }

    private final int[] mReturnValue = new int[1];

    private int getProgramParam(int paramType) {
        glGetProgramiv(mHandle, paramType, mReturnValue, 0);
        return mReturnValue[0];
    }
}
