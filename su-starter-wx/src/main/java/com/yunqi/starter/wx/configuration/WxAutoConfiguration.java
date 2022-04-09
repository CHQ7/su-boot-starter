package com.yunqi.starter.wx.configuration;

import com.yunqi.starter.wx.provider.WxApi;
import com.yunqi.starter.wx.provider.impl.WxApiImpl;
import com.yunqi.starter.wx.spi.Wxs;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author CHQ on 2022/2/28
 */
@Configuration
@ConditionalOnExpression("${su.wx.enabled:true}")
@EnableConfigurationProperties(WxProperties.class)
public class WxAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public WxApi wx(WxProperties properties) {
        // 加载配置
        Wxs.appid = properties.getAppid();
        Wxs.appsecret = properties.getAppsecret();
        return new WxApiImpl();
    }

}
