package com.wudaozi.origin.gl20.program;

import android.opengl.GLES20;

/**
 * Created by yunzhongtianjing on 16/3/5.
 */
public class Attribute {
    private final String mName;

    Attribute(String name) {
        this.mName = name;
    }

    public void setDataSource() {
        //TODO
    }

    public static Attribute create(String name, int handle, int sizeIfArray, int type) {
        //type
//GL_FLOAT
//GL_FLOAT_VEC2
//GL_FLOAT_VEC3
//GL_FLOAT_VEC4
//GL_FLOAT_MAT2
//GL_FLOAT_MAT3
//GL_FLOAT_MAT4

//    GL_BYTE
//    GL_UNSIGNED_BYTE
//    GL_SHORT
//    GL_UNSIGNED_SHORT
//    GL_FLOAT
//    GL_FIXED
//    GL_HALF_FLOAT_OES*
//        GLES20.glVertexAttribPointer(handle, );
        return null;
    }
}
