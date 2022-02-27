package com.yunqi.starter.security.annotation;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 登录认证：只有登录之后才能进入该方法
 * Created by @author JsckChin on 2022/2/27
 */
@SaCheckPermission()
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE})
public @interface RequiresPermissions {

    /**
     * 需要校验的权限码
     * @return 需要校验的权限码
     */
    @AliasFor(annotation = SaCheckPermission.class)
    String [] value() default {};

    /**
     * 验证模式：AND | OR，默认AND
     * @return 验证模式
     */
    @AliasFor(annotation = SaCheckPermission.class)
    SaMode mode() default SaMode.AND;

    /**
     * 在权限认证不通过时的次要选择，两者只要其一认证成功即可通过校验
     *
     * <p>
     * 	例1：@SaCheckPermission(value="user-add", orRole="admin")，
     * 	代表本次请求只要具有 user-add权限 或 admin角色 其一即可通过校验
     * </p>
     *
     * <p>
     * 	例2： orRole = {"admin", "manager", "staff"}，具有三个角色其一即可 <br>
     * 	例3： orRole = {"admin, manager, staff"}，必须三个角色同时具备
     * </p>
     *
     * @return /
     */
    @AliasFor(annotation = SaCheckPermission.class)
    String[] orRole() default {};
}
