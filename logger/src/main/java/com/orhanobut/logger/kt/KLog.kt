package com.orhanobut.logger.kt

/**
 * 兼容 以前的Logger
 */
typealias Logger = KLog

/**
 * Logger is a wrapper of [android.util.Log]
 * But more pretty, simple and powerful
 */
object KLog {
    val DEBUG = 3
    val ERROR = 6
    val ASSERT = 7
    val INFO = 4
    val VERBOSE = 2
    val WARN = 5

    private val DEFAULT_TAG = "PRETTYLOGGER"

    private var printer: Printer = LoggerPrinter()

    /**
     * It is used to change the tag
     *
     * @param tag is the given string which will be used in Logger as TAG
     */
    @JvmOverloads
    fun init(tag: String = DEFAULT_TAG): Settings {
        printer = LoggerPrinter()
        return printer.init(tag)
    }

    fun resetSettings() {
        printer.resetSettings()
    }

    fun t(tag: String): Printer {
        return printer.t(tag, printer.settings.methodCount)
    }

    fun t(methodCount: Int): Printer {
        return printer.t(null!!, methodCount)
    }

    fun t(tag: String, methodCount: Int): Printer {
        return printer.t(tag, methodCount)
    }

    fun log(priority: Int, tag: String, message: String, throwable: Throwable) {
        printer.log(priority, tag, message, throwable)
    }

    fun d(message: String, vararg args: Any) {
        printer.d(message, *args)
    }

    fun d(`object`: Any) {
        printer.d(`object`)
    }

    fun e(message: String?, vararg args: Any) {
        printer.e(null, message, args)
    }

    fun e(throwable: Throwable, message: String, vararg args: Any) {
        printer.e(throwable, message, *args)
    }

    fun i(message: String, vararg args: Any) {
        printer.i(message, *args)
    }

    fun v(message: String, vararg args: Any) {
        printer.v(message, *args)
    }

    fun w(message: String, vararg args: Any) {
        printer.w(message, *args)
    }

    fun wtf(message: String, vararg args: Any) {
        printer.wtf(message, *args)
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    fun json(json: String) {
        printer.json(json)
    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    fun xml(xml: String) {
        printer.xml(xml)
    }

}//no instance
/**
 * It is used to get the settings object in order to change settings
 *
 * @return the settings object
 */
