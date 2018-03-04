package com.orhanobut.logger;

import android.support.annotation.Nullable;

import com.orhanobut.logger.kt.KLog;

/**
 * Created by YZL on 2018/3/4.
 */
public class Logger {

    public static void e(@Nullable String message) {
        KLog.INSTANCE.e(message);
    }

    public static void e(@Nullable Throwable throwable, @Nullable String message) {
        KLog.INSTANCE.e(throwable, message);
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(@Nullable String json) {
        KLog.INSTANCE.json(json);
    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    public static void xml(@Nullable String xml) {
        KLog.INSTANCE.xml(xml);
    }
}
