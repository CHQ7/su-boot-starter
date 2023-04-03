package com.yunqi.starter.common.utils;

import org.slf4j.MDC;

import java.util.Map;
import java.util.UUID;

/**
 * 链路追踪工具类
 * Created by @author CHQ on 2023/4/1
 */
public class TraceIdUtil {

    public static final String TRACE_ID = "Trace-Id";

    public static String getTraceId() {
        String traceId = MDC.get(TRACE_ID);
        return traceId == null ? "" : traceId;
    }

    public static void setTraceId(String traceId)
    {
        MDC.put(TRACE_ID, traceId);
    }


    public static void remove() {
        MDC.remove(TRACE_ID);

    }

    public static void clear()
    {
        MDC.clear();
    }

    public static String generateTraceId() {
        return UUID.randomUUID().toString();
    }


    public static Map<String, String> getCopyOfContextMap() {
        return MDC.getCopyOfContextMap();
    }

    public static void setContextMap(Map<String, String> contextMap) {
        MDC.setContextMap(contextMap);
    }
}
