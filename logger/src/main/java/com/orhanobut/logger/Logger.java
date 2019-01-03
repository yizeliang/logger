package com.orhanobut.logger;


import com.orhanobut.logger.kt.KLog;

/**
 * Created by YZL on 2018/3/4.
 */
public class Logger {

    public static void e(String message, Object... args) {
        KLog.INSTANCE.e(message);
    }

    @Deprecated
    public static void e(Throwable throwable, String message, Object... args) {
        KLog.INSTANCE.e(throwable, message);
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
