package com.yunqi.starter.weixin.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author CHQ on 2022/7/27
 */
@Slf4j
@Configuration
@ConditionalOnExpression("${su.weixin.enabled:true}")
@EnableConfigurationProperties(WeiXinProperties.class)
public class WeiXinAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public WeiXinConfig getWeiXinConfig(WeiXinProperties properties) {
        WeiXinConfig config = new WeiXinConfig();
        config.setAppkey(properties.getAppkey());
        config.setAppsecret(properties.getAppsecret());
        config.setIsLog(properties.getLog());
        if(config.getIsLog()){
            log.info("自动装配 -> 微信公众号组件");
        }
        return config;
    }

}
