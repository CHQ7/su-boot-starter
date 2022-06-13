package com.yunqi.starter.security.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.yunqi.starter.common.result.IResultCode;
import com.yunqi.starter.common.result.Result;
import com.yunqi.starter.common.result.ResultCode;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 局部权限异常处理器
 * Created by @author CHQ on 2022/2/17
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityException {

    /**
     * 无此权限
     */
    @ExceptionHandler(NotPermissionException.class)
    public Result NotPermissionException(NotPermissionException e) {
        return Result.error(ResultCode.USER_NOT_PERMISSION);
    }


    /**
     * 登录校验异常
     */
    @ExceptionHandler(NotLoginException.class)
    public Result handlerNotLoginException(NotLoginException e) {
        // 判断场景值，定制化异常信息
        IResultCode resultCode;

        if(e.getType().equals(NotLoginException.NOT_TOKEN)) {
            resultCode = ResultCode.USER_NOT_LOGIN;
        }
        else if(e.getType().equals(NotLoginException.INVALID_TOKEN)) {
            resultCode = ResultCode.USER_LOGIN_INVALID;
        }
        else if(e.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            resultCode = ResultCode.USER_LOGIN_INVALID;
        }
        else if(e.getType().equals(NotLoginException.BE_REPLACED)) {
            resultCode = ResultCode.USER_LOGIN_BE_REPLACED;
        }
        else if(e.getType().equals(NotLoginException.KICK_OUT)) {
            resultCode = ResultCode.USER_LOGIN_BE_REPLACED;
        }
        else {
            resultCode = ResultCode.USER_NOT_LOGIN;
        }

        // 返回给前端
        return Result.error(resultCode);
    }

}
