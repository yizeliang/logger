package com.orhanobut.logger.kt

import com.orhanobut.logger.Logger
import org.json.JSONArray
import org.json.JSONObject
import java.io.StringReader
import java.io.StringWriter
import java.util.*
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

internal class LoggerPrinter : Printer {

    /**
     * tag is used for the Log, the name is a little different
     * in order to differentiate the logs easily with the filter
     */
    private var tag: String? = null

    /**
     * Localize single tag and method count for each thread
     */
    private val localTag = ThreadLocal<String>()
    private val localMethodCount = ThreadLocal<Int>()

    /**
     * It is used to determine log settings such as method count, thread info visibility
     */
    override val settings = Settings()

    private val methodCount: Int
        get() {
            val count = localMethodCount.get()
            var result = settings.methodCount
            if (count != null) {
                localMethodCount.remove()
                result = count
            }
            if (result < 0) {
                throw IllegalStateException("methodCount cannot be negative")
            }
            return result
        }

    init {
        init(DEFAULT_TAG)
    }

    /**
     * It is used to change the tag
     *
     * @param tag is the given string which will be used in Logger
     */
    override fun init(tag: String): Settings {
        if (tag == null) {
            throw NullPointerException("tag may not be null")
        }
        if (tag.trim { it <= ' ' }.length == 0) {
            throw IllegalStateException("tag may not be empty")
        }
        this.tag = tag
        return settings
    }

    override fun t(tag: String, methodCount: Int): Printer {
        if (tag != null) {
            localTag.set(tag)
        }
        localMethodCount.set(methodCount)
        return this
    }

    override fun d(`object`: Any?) {
        if (`object` == null) {
            return
        }
        val message: String
        if (`object`.javaClass.isArray) {
            message = Arrays.deepToString(`object` as Array<Any>)
        } else {
            message = `object`.toString()
        }
        log(DEBUG, null, message)
    }

    override fun e(message: String?) {
        e(null, message)
    }

    override fun e(throwable: Throwable?, message: String?) {
        log(ERROR, throwable, message)
    }

    override fun w(message: String?) {
        log(WARN, null, message)
    }

    override fun i(message: String?) {
        log(INFO, null, message)
    }

    override fun v(message: String?) {
        log(VERBOSE, null, message)
    }

    override fun wtf(message: String?) {
        log(ASSERT, null, message)
    }

    override fun json(json: String?) {
        var json = json
        if (Helper.isEmpty(json)) {
            e("Empty/Null json content")
            return
        }
        try {
            json = json!!.trim { it <= ' ' }
            if (json.startsWith("{")) {
                val jsonObject = JSONObject(json)
                val message = jsonObject.toString(JSON_INDENT)
                e(message)
                return
            }
            if (json.startsWith("[")) {
                val jsonArray = JSONArray(json)
                val message = jsonArray.toString(JSON_INDENT)
                e(message)
                return
            }
            return
        } catch (e: Exception) {
            e("Invalid Json error")
            e(json)
        }

    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    override fun xml(xml: String?) {
        if (Helper.isEmpty(xml)) {
            e("Empty/Null xml content")
            return
        }
        try {
            val xmlInput = StreamSource(StringReader(xml))
            val xmlOutput = StreamResult(StringWriter())
            val transformer = TransformerFactory.newInstance().newTransformer()
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
            transformer.transform(xmlInput, xmlOutput)
            e(xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">\n"))
        } catch (e: TransformerException) {
            e("Invalid xml")
        }

    }

    @Synchronized override fun log(priority: Int, tag: String, message: String?, throwable: Throwable?) {
        var message = message
        if (settings.logLevel === LogLevel.NONE) {
            return
        }
        if (throwable != null && message != null) {
            message += " : " + Helper.getStackTraceString(throwable)
        }
        if (throwable != null && message == null) {
            message = Helper.getStackTraceString(throwable)
        }
        if (message == null) {
            message = "No message/exception is set"
        }
        val methodCount = methodCount
        if (Helper.isEmpty(message)) {
            message = "Empty/NULL log message"
        }

        logTopBorder(priority, tag)
        logHeaderContent(priority, tag, methodCount)

        //get bytes of message with system's default charset (which is UTF-8 for Android)
        val bytes = message.toByteArray()
        val length = bytes.size
        if (length <= CHUNK_SIZE) {
            if (methodCount > 0) {
                logDivider(priority, tag)
            }
            logContent(priority, tag, message)
            logBottomBorder(priority, tag)
            return
        }
        if (methodCount > 0) {
            logDivider(priority, tag)
        }
        var i = 0
        while (i < length) {
            val count = Math.min(length - i, CHUNK_SIZE)
            //create a new String with system's default charset (which is UTF-8 for Android)
            logContent(priority, tag, String(bytes, i, count))
            i += CHUNK_SIZE
        }
        logBottomBorder(priority, tag)
    }

    override fun resetSettings() {
        settings.reset()
    }

    /**
     * This method is synchronized in order to avoid messy of logs' order.
     */
    @Synchronized private fun log(priority: Int, throwable: Throwable?, msg: String?) {
        if (settings.logLevel === LogLevel.NONE) {
            return
        }
        val tag = getTag()
        val message = createMessage(msg)
        log(priority, tag!!, message, throwable)
    }

    private fun logTopBorder(logType: Int, tag: String) {
        logChunk(logType, tag, TOP_BORDER)
    }

    private fun logHeaderContent(logType: Int, tag: String, methodCount: Int) {
        var methodCount = methodCount
        val trace = Thread.currentThread().stackTrace
        if (settings.isShowThreadInfo) {
            logChunk(logType, tag, HORIZONTAL_DOUBLE_LINE + " Thread: " + Thread.currentThread().name)
            logDivider(logType, tag)
        }
        var level = ""

        val stackOffset = getStackOffset(trace) + settings.methodOffset

        //corresponding method count with the current stack may exceeds the stack trace. Trims the count
        if (methodCount + stackOffset > trace.size) {
            methodCount = trace.size - stackOffset - 1
        }

        for (i in methodCount downTo 1) {
            val stackIndex = i + stackOffset
            if (stackIndex >= trace.size) {
                continue
            }
            val builder = StringBuilder()
            builder.append("║ ")
                    .append(level)
                    .append(getSimpleClassName(trace[stackIndex].className))
                    .append("")
                    .append(trace[stackIndex].methodName)
                    .append(" ")
                    .append(" (")
                    .append(trace[stackIndex].fileName)
                    .append(":")
                    .append(trace[stackIndex].lineNumber)
                    .append(")")
            level += "   "
            logChunk(logType, tag, builder.toString())
        }
    }

    private fun logBottomBorder(logType: Int, tag: String) {
        logChunk(logType, tag, BOTTOM_BORDER)
    }

    private fun logDivider(logType: Int, tag: String) {
        logChunk(logType, tag, MIDDLE_BORDER)
    }

    private fun logContent(logType: Int, tag: String, chunk: String) {
        val lines = chunk.split(System.getProperty("line.separator").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (line in lines) {
            logChunk(logType, tag, HORIZONTAL_DOUBLE_LINE + " " + line)
        }
    }

    private fun logChunk(logType: Int, tag: String, chunk: String) {
        val finalTag = formatTag(tag)
        when (logType) {
            ERROR -> settings.logAdapter.e(finalTag!!, chunk)
            INFO -> settings.logAdapter.i(finalTag!!, chunk)
            VERBOSE -> settings.logAdapter.v(finalTag!!, chunk)
            WARN -> settings.logAdapter.w(finalTag!!, chunk)
            ASSERT -> settings.logAdapter.wtf(finalTag!!, chunk)
            DEBUG -> settings.logAdapter.d(finalTag!!, chunk)
        // Fall through, log debug by default
            else -> settings.logAdapter.d(finalTag!!, chunk)
        }
    }

    private fun getSimpleClassName(name: String): String {
        val lastIndex = name.lastIndexOf("")
        return name.substring(lastIndex + 1)
    }

    private fun formatTag(tag: String): String? {
        return if (!Helper.isEmpty(tag) && !Helper.equals(this.tag, tag)) {
            this.tag + "-" + tag
        } else this.tag
    }

    /**
     * @return the appropriate tag based on local or global
     */
    private fun getTag(): String? {
        val tag = localTag.get()
        if (tag != null) {
            localTag.remove()
            return tag
        }
        return this.tag
    }

    private fun createMessage(message: String?): String {
        if (message == null) {
            return ""
        }
        return message
    }

    /**
     * Determines the starting index of the stack trace, after method calls made by this class.
     *
     * @param trace the stack trace
     * @return the stack offset
     */
    private fun getStackOffset(trace: Array<StackTraceElement>): Int {
        var i = MIN_STACK_OFFSET
        while (i < trace.size) {
            val e = trace[i]
            val fileName = e.fileName
            val name = e.className
            val method = e.methodName
            if (name != LoggerPrinter::class.java.name
                    && name != KLog::class.java.name
                    && name != Logger::class.java.name
                    && name != "VMStack"
                    && !(name == "Thread" && method == "getStackTrace")
                    && !fileName.contains("LogEx")) {
                return --i
            }
            i++
        }
        return -1
    }

    companion object {

        private val DEFAULT_TAG = "PRETTYLOGGER"

        private val DEBUG = 3
        private val ERROR = 6
        private val ASSERT = 7
        private val INFO = 4
        private val VERBOSE = 2
        private val WARN = 5

        /**
         * Android's max limit for a log entry is ~4076 bytes,
         * so 4000 bytes is used as chunk size since default charset
         * is UTF-8
         */
        private val CHUNK_SIZE = 4000

        /**
         * It is used for json pretty print
         */
        private val JSON_INDENT = 2

        /**
         * The minimum stack trace index, starts at this class after two native calls.
         */
        private val MIN_STACK_OFFSET = 3

        /**
         * Drawing toolbox
         */
        private val TOP_LEFT_CORNER = '╔'
        private val BOTTOM_LEFT_CORNER = '╚'
        private val MIDDLE_CORNER = '╟'
        private val HORIZONTAL_DOUBLE_LINE = '║'
        private val DOUBLE_DIVIDER = "════════════════════════════════════════════"
        private val SINGLE_DIVIDER = "────────────────────────────────────────────"
        private val TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER
        private val BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER
        private val MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER
    }

}
