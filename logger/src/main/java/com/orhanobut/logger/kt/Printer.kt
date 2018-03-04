package com.orhanobut.logger.kt

interface Printer {

    val settings: Settings

    fun t(tag: String, methodCount: Int): Printer

    fun init(tag: String): Settings

    fun d(message: String?, vararg args: Any)

    fun d(`object`: Any)

    fun e(message: String?, vararg args: Any)

    fun e(throwable: Throwable?, message: String?, vararg args: Any)

    fun w(message: String?, vararg args: Any)

    fun i(message: String?, vararg args: Any)

    fun v(message: String?, vararg args: Any)

    fun wtf(message: String?, vararg args: Any)

    fun json(json: String?)

    fun xml(xml: String?)

    fun log(priority: Int, tag: String, message: String?, throwable: Throwable?)

    fun resetSettings()

}
