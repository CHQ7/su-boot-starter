package com.yunqi.starter.wxpay.spi;

import com.yunqi.starter.common.json.Json;
import com.yunqi.starter.wxpay.configuration.WxPayConfig;
import com.yunqi.starter.wxpay.provider.WxPay3Api;
import com.yunqi.starter.wxpay.provider.impl.WxPay3ApiImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by @author CHQ on 2022/9/20
 */
@Slf4j
public class WxPays {

    /**
     * 配置文件 Bean
     */
    public volatile static WxPayConfig config;
    public static void setConfig(WxPayConfig config) {
        WxPays.config = config;
        if(config.getIsLog()){
            log.info("打印微信小程序配置如下 ->\n{}", Json.toJson(WxPays.config));
        }
    }

    /**
     * 底层的 WxPay3Api 对象
     */
    public static WxPay3Api v3Api = new WxPay3ApiImpl();


}
