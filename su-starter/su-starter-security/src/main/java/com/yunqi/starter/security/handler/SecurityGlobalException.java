package com.yunqi.starter.security.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.yunqi.starter.common.result.Result;
import com.yunqi.starter.common.result.ResultCode;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by @author JsckChin on 2022/2/17
 */
@RestControllerAdvice
@Order(0)
public class SecurityGlobalException {

    /**
     * 无此权限
     * @param e 异常
     * @return 	统一数据返回格式
     */
    @ExceptionHandler(NotPermissionException.class)
    public Object NotPermissionException(NotPermissionException e) {
        return Result.error(ResultCode.USER_NOT_PERMISSION);
    }


    /**
     * 权限异常
     * @param e 异常
     * @return 	统一数据返回格式
     */
    @ExceptionHandler(NotLoginException.class)
    public Object handlerNotLoginException(NotLoginException e) {
        // 打印堆栈信息
        // log.error(Lang.getStackTrace(e));
        // 判断场景值，定制化异常信息
        ResultCode resultCode;

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
