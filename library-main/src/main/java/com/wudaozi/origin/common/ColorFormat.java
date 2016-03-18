package com.wudaozi.origin.common;

import static android.opengl.GLES20.*;

/**
 * Created by yunhun.fyy on 2016/2/21.
 */
public enum ColorFormat {
    RGBA_4444(GL_RGBA4), RGBA_5551(GL_RGB5_A1), RGB_565(GL_RGB565);
    public final int GLType;

    ColorFormat(int glType) {
        this.GLType = glType;
    }
}
