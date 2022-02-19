package com.yunqi.starter.security.utils;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.yunqi.starter.common.lang.Strings;

/**
 * 安全认证工具类
 * Created by @author JsckChin on 2022/2/16
 */
public class SecurityUtil {

    /**
     * 获取当前会话账号id, 如果未登录，则抛出异常
     * @return 账号id
     */
    public static String getUserId() {
        return Strings.sNull(StpUtil.getLoginId());
    }

    /**
     * 获取当前会话的Session
     * @return Session对象
     */
    public static SaSession getSession() {
        return StpUtil.getSession(true);
    }

    /**
     * 获取当前会话账号
     * @return 账号
     */
    public static String getUserName() {
        return Strings.sNull(getSession().get("username"));
    }

    /**
     * 设置当前会话账号
     * @param username  账号
     * @return          账号
     */
    public static String setUserName(String username) {
        return Strings.sNull(getSession().set("username", username));
    }

    /**
     * 获取当前会话昵称
     * @return          昵称
     */
    public static String getUserNickname() {
        return Strings.sNull(getSession().get("nickname"));
    }

    /**
     * 设置当前会话昵称
     * @param nickname  昵称
     * @return          昵称
     */
    public static String setUserNickname(String nickname) {
        return Strings.sNull(getSession().set("nickname", nickname));
    }
}
