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
    private Shader.VertexShader mVertexShader;
    private Shader.FragmentShader mFragmentShader;

    private Map<String, Uniform> mUniforms;

    private Map<String, Attribute> mAttributes;

    public Program(String vertexShaderCode, String fragmentShaderCode) {
        super(vertexShaderCode, fragmentShaderCode);
    }

    private Map<String, Attribute> initializeAttributes(int handle) {
        glGetProgramiv(handle, GL_ACTIVE_ATTRIBUTES, returnValues, 0);
        final int attributeSize = returnValues[0];
        final int[] sizeValue = new int[1];
        final int[] typeValue = new int[1];
        final Map<String, Attribute> attributes = new HashMap<>(attributeSize);

        for (int index = 0; index < attributeSize; index++) {
            final String name = glGetActiveAttrib(handle, index, sizeValue, 0, typeValue, 0);
            final int location = glGetAttribLocation(handle, name);
            final int size = sizeValue[0];
            final int type = typeValue[0];
            attributes.put(name, Attribute.create(name, location, size, type));
        }
        return attributes;
    }

    private Map<String, Uniform> initializeUniforms(int handle) {
        glGetProgramiv(handle, GL_ACTIVE_UNIFORMS, returnValues, 0);
        final int uniformSize = returnValues[0];
        final int[] sizeValue = new int[1];
        final int[] typeValue = new int[1];
        final Map<String, Uniform> uniforms = new HashMap<>(uniformSize);
        for (int index = 0; index < uniformSize; index++) {
            final String name = glGetActiveUniform(handle, index, sizeValue, 0, typeValue, 0);
            final int location = glGetUniformLocation(handle, name);
            final int size = sizeValue[0];
            final int type = typeValue[0];
            uniforms.put(name, Uniform.create(name, location, size, type));
        }
        return uniforms;
    }

    public <T extends Uniform> T findUniformByName(String name) {
        return (T) mUniforms.get(name);
    }

    public Attribute findAttributeByName(String name) {
        return mAttributes.get(name);
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


    private int getProgramParam(int paramType) {
        glGetProgramiv(handle, paramType, returnValues, 0);
        return returnValues[0];
    }

    @Override
    protected int generate(Object... params) {
        final String vertexShaderCode = (String) params[0];
        final String fragmentShaderCode = (String) params[1];

        final int handle = glCreateProgram();
        if (handle == 0) throw new WudaoziException("Program create fail");

        this.mVertexShader = new Shader.VertexShader(vertexShaderCode);
        this.mFragmentShader = new Shader.FragmentShader(fragmentShaderCode);
        glAttachShader(handle, mVertexShader.handle);
        glAttachShader(handle, mFragmentShader.handle);
        glLinkProgram(handle);
        glGetProgramiv(handle, GL_LINK_STATUS, returnValues, 0);
        if (GL_FALSE == returnValues[0]) {
            final String log = glGetProgramInfoLog(handle);
            glDeleteProgram(handle);
            throw new WudaoziException("Program link error-%s", log);
        }

        this.mUniforms = initializeUniforms(handle);
        this.mAttributes = initializeAttributes(handle);
        return handle;
    }

    @Override
    public void delete() {
        glDeleteProgram(handle);
    }

    @Override
    public void bind() {
        glUseProgram(handle);
    }

    @Override
    public void unbind() {
        glUseProgram(0);
    }
}
