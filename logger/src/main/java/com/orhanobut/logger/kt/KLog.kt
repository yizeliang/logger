package com.orhanobut.logger.kt

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

    fun d(`object`: Any) {
        printer.d(`object`)
    }

    fun e(message: String?) {
        printer.e(null, message)
    }

    fun e(throwable: Throwable?, message: String?) {
        printer.e(throwable, message)
    }

    fun e(throwable: Throwable?) {
        printer.e(throwable, null)
    }

    fun i(message: String) {
        printer.i(message)
    }

    fun v(message: String) {
        printer.v(message)
    }

    fun w(message: String) {
        printer.w(message)
    }

    fun wtf(message: String) {
        printer.wtf(message)
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
