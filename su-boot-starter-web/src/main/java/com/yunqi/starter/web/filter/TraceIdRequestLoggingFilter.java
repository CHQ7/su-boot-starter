package com.yunqi.starter.web.filter;

import com.yunqi.starter.common.lang.Strings;
import com.yunqi.starter.common.utils.TraceIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志链路拦截器
 * Created by @author CHQ on 2023/3/29
 */
@Slf4j
public class TraceIdRequestLoggingFilter extends AbstractRequestLoggingFilter {


    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        //如果有上层调用就用上层的ID
        String traceId = request.getHeader(TraceIdUtil.TRACE_ID);
        if (Strings.isEmpty(traceId)){
            TraceIdUtil.setTraceId(TraceIdUtil.generateTraceId());
        } else {
            TraceIdUtil.setTraceId(traceId);
        }
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        log.info("日志链路拦截器 => 【TRACE_ID = {}】", TraceIdUtil.getTraceId());

        //调用结束后删除
        TraceIdUtil.remove();
    }
}
