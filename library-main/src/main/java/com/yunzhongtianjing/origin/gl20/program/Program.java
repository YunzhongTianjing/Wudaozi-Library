package com.yunzhongtianjing.origin.gl20.program;

import android.opengl.GLES20;

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

    private final int[] mReturnValues = new int[1];

    boolean isLinked() {
        glGetProgramiv(mHandle, GL_ACTIVE_ATTRIBUTES, mReturnValues, 0);
        return mReturnValues[0] == GL_TRUE;
    }

    boolean isValid() {
        glGetProgramiv(mHandle, GL_VALIDATE_STATUS, mReturnValues, 0);
        return mReturnValues[0] == GL_TRUE;
    }

    boolean isDeleted() {
        glGetProgramiv(mHandle, GL_DELETE_STATUS, mReturnValues, 0);
        return mReturnValues[0] == GL_TRUE;
    }

    private int getUniformValueSize() {
        glGetProgramiv(mHandle, GL_ACTIVE_UNIFORMS, mReturnValues, 0);
        return mReturnValues[0];
    }

    private int getAttributeValueSize() {
        glGetProgramiv(mHandle, GL_ACTIVE_ATTRIBUTES, mReturnValues, 0);
        return mReturnValues[0];
    }

}
