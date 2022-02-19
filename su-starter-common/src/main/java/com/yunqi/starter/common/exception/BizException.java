package com.yunqi.starter.common.exception;

import com.yunqi.starter.common.result.ResultCode;


/**
 * 业务异常
 * Created by @author JsckChin on 2022/2/15
 */
public class BizException extends BaseException{

    /**
     * 错误码
     */
    protected Integer code;
    /**
     * 错误信息
     */
    protected String msg;

    /**
     * 构造函数，默认错误码
     * @param msg 错误信息提示
     */
    public BizException(String msg) {
        super(msg);
        this.code = ResultCode.FAILURE.getCode();
        this.msg = msg;
    }

    /**
     * 构造函数
     * @param code 错误码
     * @param msg  错误信息提示
     */
    public BizException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    /**
     * 构造函数
     * @param resultCode 返回状态码枚举类
     */
    public BizException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
