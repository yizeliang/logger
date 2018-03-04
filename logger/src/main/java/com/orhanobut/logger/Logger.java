package com.orhanobut.logger;

import com.orhanobut.logger.kt.KLog;

/**
 * Created by YZL on 2018/3/4.
 */
public class Logger {
    public static void d(String message, Object... args) {
        KLog.INSTANCE.d(message, args);
    }

    public static void d(Object object) {
        KLog.INSTANCE.d(object);
    }

    public static void e(String message, Object... args) {
        KLog.INSTANCE.e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        KLog.INSTANCE.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        KLog.INSTANCE.i(message, args);
    }

    public static void v(String message, Object... args) {
        KLog.INSTANCE.v(message, args);
    }

    public static void w(String message, Object... args) {
        KLog.INSTANCE.w(message, args);
    }

    public static void wtf(String message, Object... args) {
        KLog.INSTANCE.wtf(message, args);
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(String json) {
        KLog.INSTANCE.json(json);
    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    public static void xml(String xml) {
        KLog.INSTANCE.xml(xml);
    }
}
