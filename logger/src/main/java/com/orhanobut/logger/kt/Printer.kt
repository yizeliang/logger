package com.orhanobut.logger.kt

interface Printer {

    val settings: Settings

    fun t(tag: String, methodCount: Int): Printer

    fun init(tag: String): Settings

    fun d(`object`: Any?)

    fun e(message: String?)

    fun e(throwable: Throwable?, message: String? = null)

    fun w(message: String?)

    fun i(message: String?)

    fun v(message: String?)

    fun wtf(message: String?)

    fun json(json: String?)

    fun xml(xml: String?)

    fun log(priority: Int, tag: String, message: String?, throwable: Throwable?)

    fun resetSettings()

}
