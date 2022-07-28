package com.yunqi.starter.weixin.spi;

import com.yunqi.starter.common.json.Json;
import com.yunqi.starter.weixin.configuration.WeiXinConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by @author CHQ on 2022/7/27
 */
@Slf4j
public class WeiXins {

    /**
     * 配置文件 Bean
     */
    public volatile static WeiXinConfig config;
    public static void setConfig(WeiXinConfig config) {
        WeiXins.config = config;
        if(config.getIsLog()){
            log.info("打印微信公众号配置如下 ->\n{}", Json.toJson(WeiXins.config));
        }
    }
}
