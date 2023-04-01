package com.yunqi.starter.web.filter;

import com.yunqi.starter.common.lang.Strings;
import com.yunqi.starter.common.utils.TraceIdUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 日志链路过滤器
 * Created by @author CHQ on 2023/4/1
 */
@Slf4j
public class TraceIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //如果有上层调用就用上层的ID
        String traceId = request.getHeader(TraceIdUtil.TRACE_ID);
        if (Strings.isEmpty(traceId)){
            traceId = TraceIdUtil.generateTraceId();
        }
        TraceIdUtil.setTraceId(traceId);

        try {
            chain.doFilter(servletRequest, servletResponse);
        } finally {
            TraceIdUtil.clear();
        }
    }
}
