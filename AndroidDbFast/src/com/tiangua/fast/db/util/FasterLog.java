package com.tiangua.fast.db.util;

import android.util.Log;

public class FasterLog {
    public static void d(String debug) {
        Log.d("FasterLog", debug);
    }

    public static void i(String info) {
        Log.i("FasterLog", info);
    }

    public static void w(String warn) {
        Log.w("FasterLog", warn);
    }

    public static void v(String verbose) {
        Log.v("FasterLog", verbose);

    }

    public static void E(Class<?> clz, Throwable throwable) {
        Log.e("FasterLog", clz.getClass().getName() + " / " + throwable.getMessage(), throwable);

    }
}
