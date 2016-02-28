package com.yunzhongtianjing.origin.common;

import android.opengl.GLES20;
import android.opengl.GLU;
import android.util.Log;

import com.yunzhongtianjing.exception.WudaoziException;
import com.yunzhongtianjing.log.WLog;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import static android.opengl.GLES20.*;

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
