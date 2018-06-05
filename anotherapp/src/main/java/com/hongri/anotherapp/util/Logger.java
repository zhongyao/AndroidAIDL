package com.hongri.anotherapp.util;

import android.util.Log;

/**
 * Created by zhongyao on 2018/6/1.
 */

public class Logger {
    public static final String TAG = "hongri";

    public static void D(String message) {
        Log.d(TAG, message);
    }

    public static void E(String message){
        Log.e(TAG,message);
    }
}
