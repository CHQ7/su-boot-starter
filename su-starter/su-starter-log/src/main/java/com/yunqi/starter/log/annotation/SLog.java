package com.yunqi.starter.log.annotation;

import com.yunqi.starter.log.enums.LogType;

import java.lang.annotation.*;

/**
 * 注解日志系统
 * Created by @author JsckChin on 2022/2/16
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SLog {

    /** 日志类型 */
    LogType type() default LogType.OTHER;

    /** 日志标签 */
    String tag() default "";

    /** 日志内容 */
    String value() default "";

    /** 是否记录传递参数 */
    boolean param() default true;

    /**  记录执行结果 */
    boolean result() default true;
}
