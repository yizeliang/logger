package com.orhanobut.logger.kt

import android.os.Handler
import android.os.Looper
import android.util.Log

internal class AndroidLogAdapter : LogAdapter {
    companion object {
        const val display = 10L
    }

    private val handle = Handler(Looper.getMainLooper())
    override fun d(tag: String, message: String) {
        handle.postDelayed({
            Log.d(tag, message)
        }, display)
    }

    override fun e(tag: String, message: String) {
        handle.postDelayed({
            Log.e(tag, message)
        }, display)

    }

    override fun w(tag: String, message: String) {
        handle.postDelayed({
            Log.w(tag, message)
        }, display)
    }

    override fun i(tag: String, message: String) {
        handle.postDelayed({
            Log.i(tag, message)
        }, display)
    }

    override fun v(tag: String, message: String) {
        handle.postDelayed({
            Log.v(tag, message)
        }, display)
    }

    override fun wtf(tag: String, message: String) {
        handle.postDelayed({
            Log.wtf(tag, message)
        }, display)
    }
}
