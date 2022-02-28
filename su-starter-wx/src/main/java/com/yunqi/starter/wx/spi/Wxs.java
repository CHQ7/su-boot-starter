package com.yunqi.starter.wx.spi;

import com.yunqi.starter.wx.provider.WxApi;
import com.yunqi.starter.wx.provider.impl.WxApiImpl;
import org.nutz.lang.util.NutMap;

/**
 * 微信平台基础支持对象
 * Created by @author JsckChin on 2022/2/28
 */
public class Wxs {

    public static String domain = "https://api.weixin.qq.com";

    /** 小程序ID */
    public static String appid = "";

    /** 小程序密钥 */
    public static String appsecret = "";


    /**
     * 底层的 WxApi 对象
     */
    public static WxApi wxApi = new WxApiImpl();

    // =================== 获取Api 相关 ===================

    /**
     * 登录凭证校验
     * @param code 登录时获取的 code
     * @return     请求数据
     */
    public static NutMap jscode2session(String code){
        return wxApi.jscode2session(code);
    }




}
