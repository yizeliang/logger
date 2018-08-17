package com.orhanobut.logger.kt

import android.os.Handler
import android.os.Looper
import android.util.Log

internal class AndroidLogAdapter : LogAdapter {
    private val handle = Handler(Looper.getMainLooper())
    override fun d(tag: String, message: String) {
        handle.postDelayed({
            Log.d(tag, message)
        }, 1)
    }

    override fun e(tag: String, message: String) {
        handle.postDelayed({
            Log.e(tag, message)
        }, 1)

    }

    override fun w(tag: String, message: String) {
        handle.postDelayed({
            Log.w(tag, message)
        }, 1)
    }

    override fun i(tag: String, message: String) {
        handle.postDelayed({
            Log.i(tag, message)
        }, 1)
    }

    override fun v(tag: String, message: String) {
        handle.postDelayed({
            Log.v(tag, message)
        }, 1)
    }

    override fun wtf(tag: String, message: String) {
        handle.postDelayed({
            Log.wtf(tag, message)
        }, 1)
    }
}
