package com.yunqi.starter.common.utils;

import com.yunqi.starter.common.lang.Strings;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 线程MDC包装类
 * Created by @author CHQ on 2023/4/1
 */
public class ThreadMdcUtil {

    public static void setTraceIdIfAbsent() {
        if (Strings.isBlank(TraceIdUtil.getTraceId())) {
            TraceIdUtil.setTraceId(TraceIdUtil.generateTraceId());
        }
    }

    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                TraceIdUtil.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                return callable.call();
            } finally {
                TraceIdUtil.clear();
            }
        };
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                TraceIdUtil.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                runnable.run();
            } finally {
                TraceIdUtil.clear();
            }
        };
    }
}
