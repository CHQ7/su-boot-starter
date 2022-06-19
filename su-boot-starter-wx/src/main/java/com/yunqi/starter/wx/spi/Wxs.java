package com.yunqi.starter.wx.spi;

import com.yunqi.starter.common.json.Json;
import com.yunqi.starter.wx.configuration.WxConfig;
import com.yunqi.starter.wx.provider.WxApi;
import com.yunqi.starter.wx.provider.impl.WxApiImpl;
import com.yunqi.starter.wx.util.WxToken;
import lombok.extern.slf4j.Slf4j;
import org.nutz.lang.util.NutMap;

/**
 * 微信平台基础支持对象
 * Created by @author CHQ on 2022/2/28
 */
@Slf4j
public class Wxs {

    /**
     * 配置文件 Bean
     */
    public volatile static WxConfig config;
    public static void setConfig(WxConfig config) {
        Wxs.config = config;
        if(config.getIsLog()){
            log.info("打印微信小程序配置如下 ->\n{}", Json.toJson(Wxs.config));
        }
    }

    /**
     * 底层的 WxApi 对象
     */
    public static WxApi wxApi = new WxApiImpl();

    // =================== 获取Api 相关 ===================

    /**
     * 获取凭证
     * @return 微信小程序凭证
     */
    public static String getToken(){
        return WxToken.getToken();
    }

    /**
     * 获取凭证
     * @return 请求数据
     */
    public static NutMap getTicket(){
        return wxApi.getTicket();
    }


    /**
     * 登录凭证校验
     * @param code 登录时获取的 code
     * @return     请求数据
     */
    public static NutMap jscode2session(String code){
        return wxApi.jscode2session(code);
    }


    /**
     * code换取用户手机号
     * @param code  手机号获取凭证
     * @return      手机号
     */
    public static String getPhoneNumber(String code){
        return wxApi.getPhoneNumber(code);
    }



}
