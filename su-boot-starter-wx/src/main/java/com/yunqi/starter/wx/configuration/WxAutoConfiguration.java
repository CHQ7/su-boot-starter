package com.yunqi.starter.wx.configuration;

import com.yunqi.starter.wx.provider.WxApi;
import com.yunqi.starter.wx.provider.impl.WxApiImpl;
import com.yunqi.starter.wx.spi.Wxs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author CHQ on 2022/6/19
 */
@Slf4j
@Configuration
@ConditionalOnExpression("${su.wx.enabled:true}")
@EnableConfigurationProperties(WxProperties.class)
public class WxAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public WxConfig wx(WxProperties properties) {
        WxConfig config = new WxConfig();
        config.setAppkey(properties.getAppkey());
        config.setAppsecret(properties.getAppsecret());
        config.setIsLog(properties.getLog());
        if(config.getIsLog()){
            log.info("自动装配 -> 微信小程序组件");
        }
        return config;
    }

}
