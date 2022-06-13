package com.yunqi.starter.web.exception;

import com.yunqi.starter.common.exception.BizException;
import com.yunqi.starter.common.lang.mvc.Mvcs;
import com.yunqi.starter.common.result.Result;
import com.yunqi.starter.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * Created by @author CHQ on 2022/2/17
 */
@Slf4j
@RestControllerAdvice
public class GlobalException {

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e) {
        log.error("请求地址'{}',发生未知异常.", Mvcs.getReq().getRequestURI(), e);
        return Result.error(e.getMessage());
    }

    /**
     * 系统异常处理方法
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("请求地址'{}',发生系统异常.", Mvcs.getReq().getRequestURI(), e);
        return Result.error(e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BizException.class)
    public Result handleBizException(BizException e) {
        return Result.error(ResultCode.FAILURE, e.getMessage());
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        log.error("请求地址'{}',不支持'{}'请求", Mvcs.getReq().getRequestURI(), e.getMethod());
        return Result.error(e.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e) {
        log.error("请求地址'{}',发生自定义验证异常.", Mvcs.getReq().getRequestURI(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return Result.error(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("请求地址'{}',发生自定义验证异常.", Mvcs.getReq().getRequestURI(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return Result.error(message);
    }

}
