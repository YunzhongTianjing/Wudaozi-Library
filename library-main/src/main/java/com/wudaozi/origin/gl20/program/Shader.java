package com.wudaozi.origin.gl20.program;

import com.wudaozi.exception.WudaoziException;
import com.wudaozi.origin.OpenGLObject;

import static android.opengl.GLES20.*;

/**
 * Created by yunzhongtianjing on 16/3/14.
 */
class Shader extends OpenGLObject {

    Shader(int type, String sourceCode) {
        super(type, sourceCode);
    }

    boolean isCompiled() {
        glGetShaderiv(handle, GL_COMPILE_STATUS, returnValues, 0);
        return returnValues[0] == GL_TRUE;
    }

    boolean isDeleted() {
        glGetShaderiv(handle, GL_DELETE_STATUS, returnValues, 0);
        return returnValues[0] == GL_TRUE;
    }

    @Override
    protected int generate(Object... params) {
        final int type = (Integer) params[0];
        final String sourceCode = (String) params[1];

        final int handle = glCreateShader(type);
        if (handle == 0)
            throw new WudaoziException("%s create fail", this.getClass().getName());
        glShaderSource(handle, sourceCode);
        glCompileShader(handle);

        glGetShaderiv(handle, GL_COMPILE_STATUS, returnValues, 0);
        if (GL_FALSE == returnValues[0]) {
            final String log = glGetShaderInfoLog(handle);
            glDeleteShader(handle);
            throw new WudaoziException("%s compile error-%s", this.getClass().getName(), log);
        }
        return handle;
    }

    @Override
    public void delete() {
        glDeleteShader(handle);
    }

    @Override
    public void bind() {

    }

    @Override
    public void unbind() {

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
