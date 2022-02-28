package com.yunqi.starter.wx.provider;

import org.nutz.lang.util.NutMap;

/**
 * Created by @author JsckChin on 2022/2/28
 */
public interface WxApi {


    /**
     * 登录凭证校验
     * @param code 登录时获取的 code
     * @return     请求数据
     */
    NutMap jscode2session(String code);
}
