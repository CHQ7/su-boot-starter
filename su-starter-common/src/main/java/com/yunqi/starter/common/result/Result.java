package com.yunqi.starter.common.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一数据返回格式
 * Created by @author JsckChin on 2022/1/28
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private int code;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 时间戳
     */
    private long time;

    /**
     * 业务数据
     */
    private Object data;


    /**
     * 实例化Result
     */
    public static <T> Result<T> NEW() {
        return new Result<>();
    }

    /**
     * 构造函数
     */
    private Result() {
        this.time = System.currentTimeMillis();
    }

    /**
     * 构造函数
     * @param resultCode 状态码枚举
     */
    private Result(IResultCode resultCode) {
        this(resultCode, null, resultCode.getMsg());
    }

    /**
     * 构造函数
     * @param resultCode 状态码枚举
     * @param msg        返回内容
     */
    private Result(IResultCode resultCode, String msg) {
        this(resultCode, null, msg);
    }

    /**
     * 构造函数
     * @param resultCode 状态码枚举
     * @param data  返回数据
     */
    private Result(IResultCode resultCode, T data) {
        this(resultCode, data, resultCode.getMsg());
    }


    /**
     * 有参构造函数
     * @param resultCode 状态码枚举
     * @param data  返回数据
     * @param msg
     */
    private Result(IResultCode resultCode, T data, String msg) {
        this(resultCode.getCode(), data, msg);
    }


    /**
     * 全参构造函数
     * @param code    状态码
     * @param data    返回数据
     * @param msg     返回内容
     */
    private Result(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.time = System.currentTimeMillis();
    }

    /**
     * 更新状态码
     * @param code  状态码
     * @return Result
     */
    public Result<T> addCode(int code) {
        this.code = code;
        return this;
    }

    /**
     * 更新返回内容
     * @param msg  返回内容
     * @return Result
     */
    public Result<T> addMsg(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * 更新返回数据
     * @param data  返回数据
     * @return Result
     */
    public Result<T> addData(T data) {
        this.data = data;
        return this;
    }


    /**
     * 构造一个成功消息的结果返回
     * @param <T>   泛型
     * @return      统一返回格式
     */
    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS, ResultCode.SUCCESS.getMsg());
    }

    /**
     * 构造一个成功消息的结果返回
     * @param resultCode    状态码枚举
     * @param <T>           泛型
     * @return              统一返回格式
     */
    public static <T> Result<T> success(IResultCode resultCode) {
        return new Result<>(resultCode);
    }

    /**
     * 构造一个有参成功消息的结果返回
     * @param msg     返回内容
     * @param <T>     泛型
     * @return        统一返回格式
     */
    public static <T> Result<T> success(String msg) {
        return new Result<>(ResultCode.SUCCESS, msg);
    }

    /**
     * 构造一个有参成功消息的结果返回
     * @param resultCode    状态码枚举
     * @param msg           返回内容
     * @param <T>           泛型
     * @return              统一返回格式
     */
    public static <T> Result<T> success(ResultCode resultCode, String msg) {
        return new Result<>(resultCode, msg);
    }

    /**
     * 构造一个有参成功消息的结果返回
     * @param code          状态码
     * @param msg           返回内容
     * @param <T>           泛型
     * @return              统一返回格式
     */
    public static <T> Result<T> success(int code, String msg) {
        return new Result<>(code, null, msg);
    }

    /**
     * 构造一个有参成功消息的结果返回
     * @param data          返回数据
     * @param <T>           泛型
     * @return              统一返回格式
     */
    public static <T> Result<T> success(T data) {
        return success(data, ResultCode.SUCCESS.getMsg());
    }

    /**
     * 构造一个有参成功消息的结果返回
     * @param data          返回数据
     * @param msg           返回内容
     * @param <T>           泛型
     * @return              统一返回格式
     */
    public static <T> Result<T> success(T data, String msg) {
        return success(ResultCode.SUCCESS.getCode(), data, msg);
    }

    /**
     * 构造一个有参成功消息的结果返回
     * @param code          状态码
     * @param data          返回数据
     * @param msg           返回内容
     * @param <T>           泛型
     * @return              统一返回格式
     */
    public static <T> Result<T> success(int code, T data, String msg) {
        return new Result<>(code, data, data == null ? ResultCode.FAILURE.getMsg() : msg);
    }

    /**
     * 构造一个有参成功消息的结果返回
     * @param data  返回数据
     * @param <T>   泛型
     * @return      统一返回格式
     */
    public static <T> Result<T> data(T data) {
        return data(data, ResultCode.SUCCESS.getMsg());
    }

    /**
     * 构造一个有参成功消息的结果返回
     * @param data  返回数据
     * @param msg   返回内容
     * @param <T>   泛型
     * @return      统一返回格式
     */
    public static <T> Result<T> data(T data, String msg) {
        return data(ResultCode.SUCCESS.getCode(), data, msg);
    }

    /**
     * 构造一个有参消息的结果返回
     * @param code  状态码
     * @param data  返回数据
     * @param msg   返回内容
     * @param <T>   泛型
     * @return      统一返回格式
     */
    public static <T> Result<T> data(int code, T data, String msg) {
        return new Result<>(code, data, data == null ? ResultCode.NULL_DATA_ERROR.getMsg() : msg);
    }

    /**
     * 构造一个失败消息的结果返回
     * @param <T>   泛型
     * @return      统一返回格式
     */
    public static <T> Result<T> error() {
        return new Result<>(ResultCode.FAILURE, ResultCode.FAILURE.getMsg());
    }

    /**
     * 构造一个有参失败消息的结果返回
     * @param msg   返回内容
     * @param <T>   泛型
     * @return      统一返回格式
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(ResultCode.FAILURE, msg);
    }



    /**
     * 构造一个有参失败消息的结果返回
     * @param code  状态码
     * @param msg   返回内容
     * @param <T>   泛型
     * @return      统一返回格式
     */
    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, null, msg);
    }

    /**
     * 构造一个有参失败消息的结果返回
     * @param resultCode    状态码枚举
     * @param <T>   泛型
     * @return      统一返回格式
     */
    public static <T> Result<T> error(IResultCode resultCode) {
        return new Result<>(resultCode);
    }

    /**
     * 构造一个有参失败消息的结果返回
     * @param resultCode    状态码枚举
     * @param msg   返回内容
     * @param <T>   泛型
     * @return      统一返回格式
     */
    public static <T> Result<T> error(IResultCode resultCode, String msg) {
        return new Result<>(resultCode, msg);
    }

    /**
     * 通过入参的状态判断返回的结果
     * @param flag  状态
     * @param <T>   泛型
     * @return      统一返回格式
     */
    public static <T> Result<T> condition(boolean flag) {
        return flag ? success(ResultCode.SUCCESS.getMsg()) : error(ResultCode.FAILURE.getMsg());
    }
}
