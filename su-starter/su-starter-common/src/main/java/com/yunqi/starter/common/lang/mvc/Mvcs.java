package com.yunqi.starter.common.lang.mvc;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Mvc 相关帮助函数
 * Created by @author JsckChin on 2022/2/18
 */
public abstract class Mvcs {

    /**
     * 获取 HTTP 请求对象
     *
     * @return HTTP 请求对象
     */
    public static final HttpServletRequest getReq() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
}
