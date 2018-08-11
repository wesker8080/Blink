package com.eliteai.blink.utils;

import android.util.Log;

import com.eliteai.blink.C;

/**
 * @author MR.ZHANG
 * @create 2018-08-10 14:48
 */
public class LogUtlis {
    public static void e(String tag, String msg) {
        if (C.DEBUG_LOG_ON) {
            Log.e(tag,msg);
        }
    }
    public static void d(String tag, String msg) {
        if (C.DEBUG_LOG_ON) {
            Log.d(tag,msg);
        }
    }
    public static void i(String tag, String msg) {
        if (C.DEBUG_LOG_ON) {
            Log.i(tag,msg);
        }
    }
}
