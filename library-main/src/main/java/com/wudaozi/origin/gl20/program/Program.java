package com.wudaozi.origin.gl20.program;

import com.wudaozi.exception.WudaoziException;

import java.util.HashMap;
import java.util.Map;

import static android.opengl.GLES20.*;

/**
 * Created by yunzhongtianjing on 16/3/14.
 */
public class Program {
    private final Shader.VertexShader mVertexShader;
    private final Shader.FragmentShader mFragmentShader;

    private int mHandle;
    private Map<String, Uniform> mUniforms;

    public Program(String vertexShaderCode, String fragmentShaderCode) {
        this.mVertexShader = new Shader.VertexShader(vertexShaderCode);
        this.mFragmentShader = new Shader.FragmentShader(fragmentShaderCode);
    }

    public void initialize() {
        mHandle = getHandle();
        mUniforms = createUniforms();
    }

    private Map<String, Uniform> createUniforms() {
        final int uniformSize = getUniformValueSize();
        final int[] sizeValue = new int[1];
        final int[] typeValue = new int[1];
        final Map<String, Uniform> result = new HashMap<>();
        for (int index = 0; index < uniformSize; index++) {
            final String name = glGetActiveUniform(mHandle, index, sizeValue, 0, typeValue, 0);
            final int location = glGetUniformLocation(mHandle, name);
            final int size = sizeValue[0];
            final int type = typeValue[0];
            mUniforms.put(name, Uniform.create(name, location, size, type));
        }
        return null;
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
            throw new WudaoziException("Program link error-%s", log);
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
