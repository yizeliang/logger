package com.orhanobut.logger.kt

import android.util.Log

internal class AndroidLogAdapter : LogAdapter {
    override fun d(tag: String, message: String) {
        Thread.sleep(1)
        Log.d(tag, message)
    }

    override fun e(tag: String, message: String) {
        Thread.sleep(1)
        Log.e(tag, message)
    }

    override fun w(tag: String, message: String) {
        Thread.sleep(1)
        Log.w(tag, message)
    }

    override fun i(tag: String, message: String) {
        Thread.sleep(1)
        Log.i(tag, message)
    }

    override fun v(tag: String, message: String) {
        Thread.sleep(1)
        Log.v(tag, message)
    }

    override fun wtf(tag: String, message: String) {
        Thread.sleep(1)
        Log.wtf(tag, message)
    }
}
