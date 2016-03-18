package com.wudaozi.origin.common;

import android.opengl.GLES20;
import android.opengl.GLU;

import com.wudaozi.log.WLog;

/**
 * Created by yunhun.fyy on 2016/2/22.
 */
public class Utils {
    private Utils() {
    }


    public static void checkGLError(String operation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            WLog.e("GLError.code{%s}.message{%s}.withOperation{%s}", error, GLU.gluErrorString(error), operation);
            throw new RuntimeException(String.format("GLError:%s-%s", error, GLU.gluErrorString(error)));
        }
    }

}
