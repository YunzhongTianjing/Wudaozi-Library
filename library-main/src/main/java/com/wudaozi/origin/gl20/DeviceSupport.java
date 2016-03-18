package com.wudaozi.origin.gl20;

import static android.opengl.GLES20.*;

/**
 * Created by yunzhongtianjing on 16/2/28.
 */
public class DeviceSupport {
    private static DeviceSupport sInstance;

    public final boolean supportIntIBO;

    private DeviceSupport() {
        this.supportIntIBO = glGetString(GL_EXTENSIONS).contains("GL_OES_element_index_uint");
    }

    public static DeviceSupport getInstance() {
        //TODO judge Thread,only GLThread is valid;
        if (null == sInstance)
            sInstance = new DeviceSupport();
        return sInstance;
    }
}
