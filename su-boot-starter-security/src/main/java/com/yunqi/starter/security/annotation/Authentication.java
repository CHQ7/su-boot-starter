package com.yunqi.starter.security.annotation;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.yunqi.starter.security.spi.UserSecurityUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 登录认证( 用户版 USER )：只有登录之后才能进入该方法
 * <p> 可标注在函数、类上（效果等同于标注在此类的所有方法上）
 * Created by @author CHQ on 2022/2/27
 */
@SaCheckLogin(type = UserSecurityUtil.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE})
public @interface Authentication {
}
