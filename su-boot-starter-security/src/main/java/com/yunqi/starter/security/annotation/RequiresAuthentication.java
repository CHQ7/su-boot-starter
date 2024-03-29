package com.yunqi.starter.security.annotation;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.yunqi.starter.security.spi.SecurityUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 登录认证：只有登录之后才能进入该方法
 * Created by @author CHQ on 2022/2/27
 */
@SaCheckLogin(type = SecurityUtil.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE})
public @interface RequiresAuthentication {
}
