package com.yunqi.starter.common.exception;

/**
 * 异常基类
 * Created by @author JsckChin on 2022/1/28
 */
public class BaseException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public BaseException(String message) {
        super(message);
    }
}
