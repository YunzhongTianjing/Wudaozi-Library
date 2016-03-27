package com.wudaozi.origin.gl20.program;

import com.wudaozi.exception.WudaoziException;
import com.wudaozi.origin.OpenGLObject;

import java.util.HashMap;
import java.util.Map;

import static android.opengl.GLES20.*;

/**
 * Created by yunzhongtianjing on 16/3/14.
 */
public class Program extends OpenGLObject {
    private final Shader.VertexShader mVertexShader;
    private final Shader.FragmentShader mFragmentShader;

    private final Map<String, Uniform> mUniforms = new HashMap<>();

    private final Map<String, Attribute> mAttributes = new HashMap<>();

    public Program(String vertexShaderCode, String fragmentShaderCode) {
        this.mVertexShader = new Shader.VertexShader(vertexShaderCode);
        this.mFragmentShader = new Shader.FragmentShader(fragmentShaderCode);
    }

    private void initializeAttributes(int handle) {
        final int attributeSize = getAttributeValueSize();
        final int[] sizeValue = new int[1];
        final int[] typeValue = new int[1];
        for (int index = 0; index < attributeSize; index++) {
            final String name = glGetActiveAttrib(handle, index, sizeValue, 0, typeValue, 0);
            final int location = glGetAttribLocation(handle, name);
            final int size = sizeValue[0];
            final int type = typeValue[0];
            mAttributes.put(name, Attribute.create(name, location, size, type));
        }
    }

    private void initializeUniforms(int handle) {
        final int uniformSize = getUniformValueSize();
        final int[] sizeValue = new int[1];
        final int[] typeValue = new int[1];
        for (int index = 0; index < uniformSize; index++) {
            final String name = glGetActiveUniform(handle, index, sizeValue, 0, typeValue, 0);
            final int location = glGetUniformLocation(handle, name);
            final int size = sizeValue[0];
            final int type = typeValue[0];
            mUniforms.put(name, Uniform.create(name, location, size, type));
        }
    }

    public <T extends Uniform> T findUniformByName(String name) {
        return (T) mUniforms.get(name);
    }

    public Attribute findAttributeByName(String name) {
        return mAttributes.get(name);
    }

    private int createProgram() {
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

    @Override
    protected int generate() {
        final int handle = createProgram();
        initializeUniforms(handle);
        initializeAttributes(handle);
        return handle;
    }

    @Override
    public void delete() {
        glDeleteProgram(mHandle);
    }

    @Override
    public void bind() {
        glUseProgram(mHandle);
    }

    @Override
    public void unbind() {
        glUseProgram(0);
    }
}
