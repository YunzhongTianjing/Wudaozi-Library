package com.yunzhongtianjing.origin;

import android.opengl.GLES20;
import android.opengl.GLU;
import android.util.Log;

import static android.opengl.GLES20.*;

/**
 * Created by yunhun.fyy on 2016/2/22.
 */
public class Utils {
    private Utils() {
    }


    protected void checkGLError(String operation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            final String errorMessage = String.format("GLError.code{%s}.message{%s}.withOperation{%s}",
                    error, GLU.gluErrorString(error), operation);
            Log.e("Wudaozi", errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }
}
