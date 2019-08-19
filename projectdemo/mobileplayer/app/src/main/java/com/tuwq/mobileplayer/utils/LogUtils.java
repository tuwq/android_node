package com.tuwq.mobileplayer.utils;

import android.util.Log;

public class LogUtils {

    private static final boolean ENABLE = true;

    public static void e(String tag, String msg) {
        if (ENABLE) {
            Log.e("log_" + tag, msg);
        }
    }

    public static void e(Class cls, String msg) {
        if (ENABLE) {
            Log.e("log_" + cls.getSimpleName(), msg);
        }
    }
}
