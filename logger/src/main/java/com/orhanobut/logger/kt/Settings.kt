package com.orhanobut.logger.kt

class Settings {

    var methodCount = 2
        private set
    var isShowThreadInfo = true
        private set
    var methodOffset = 0
        private set
    var logAdapter: LogAdapter = AndroidLogAdapter()

    /**
     * Determines to how logs will be printed
     */
    var logLevel = LogLevel.FULL
        private set

    fun hideThreadInfo(): Settings {
        isShowThreadInfo = false
        return this
    }

    fun methodCount(methodCount: Int): Settings {
        var methodCount = methodCount
        if (methodCount < 0) {
            methodCount = 0
        }
        this.methodCount = methodCount
        return this
    }

    fun logLevel(logLevel: LogLevel): Settings {
        this.logLevel = logLevel
        return this
    }

    fun methodOffset(offset: Int): Settings {
        this.methodOffset = offset
        return this
    }

    fun logAdapter(logAdapter: LogAdapter): Settings {
        this.logAdapter = logAdapter
        return this
    }


    fun reset() {
        methodCount = 2
        methodOffset = 0
        isShowThreadInfo = true
        logLevel = LogLevel.FULL
    }
}
