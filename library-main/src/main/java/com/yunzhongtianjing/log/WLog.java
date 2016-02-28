package com.yunzhongtianjing.log;

import android.util.Log;

/**
 * Created by yunzhongtianjing on 16/2/22.
 */
public class WLog {
    private static final String TAG = "Wudaozi";

    private WLog() {
    }

    public static void i(String message, Object... params) {
        Log.i(TAG, String.format(message, params));
    }

    public static void e(String message, Object... params) {
        Log.e(TAG, String.format(message, params));
    }

    public static void w(String message, Object... params) {
        Log.w(TAG, String.format(message, params));
    }
}