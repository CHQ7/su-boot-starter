package com.yunqi.starter.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回状态码枚举类
 * Created by @author JsckChin on 2022/1/28
 */
@Getter
@AllArgsConstructor
public enum ResultCode implements IResultCode{

    SUCCESS(20000,"成功"),
    FAILURE(50001,"失败"),

    NOT_FOUND(50404,"服务未找到"),
    SERVER_ERROR(50500,"系统内部异常，异常信息："),

    USER_NOT_LOGIN(50009, "账号未登录"),
    USER_NOT_PERMISSION(50010, "无此权限"),
    USER_LOGIN_INVALID(50014, "账号登录失效"),
    USER_LOGIN_BE_REPLACED(50015, "账号已被顶下线"),

    TOO_MANY_REQUESTS(200429, "请求次数过多"),
    IOC_ERROR(500000,"IOC对象加载异常"),
    NULL_DATA_ERROR(500100,"数据不存在"),
    HAVE_DATA_ERROR(500110,"数据已存在"),
    PARAM_ERROR(500200,"参数错误"),
    XSS_SQL_ERROR(500300,"传参被拦截"),
    BLACKLIST_ERROR(500400,"IP黑名单"),
    DAO_ERROR(500600,"DAO数据库查询错误"),
    DEMO_ERROR(500700,"演示环境，限制操作"),

    USER_NOT_ROLE(600099, "无此角色"),
    USER_NOT_FOUND(600101, "用户不存在"),
    USER_DISABLED(600102, "用户被禁用"),
    USER_LOCKED(600103, "用户已锁定"),
    USER_NAME_ERROR(600104, "用户名错误"),
    USER_PWD_ERROR(600105, "用户密码错误"),
    USER_PWD_EXPIRED(600106, "用户密码过期"),
    USER_VERIFY_ERROR(600107, "验证码错误"),
    USER_LOGIN_FAIL(600108, "用户登录失败");

    /**
     * 业务状态码
     */
    private final Integer code;

    /**
     * 业务信息描述
     */
    private final String msg;


}
