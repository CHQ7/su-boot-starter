package com.yunqi.starter.dingtalk.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 自动装配
 * Created by @author CHQ on 2022/4/1
 */
@Slf4j
@Configuration
@ConditionalOnExpression("${su.dingtalk.enabled:true}")
@EnableConfigurationProperties(DingtalkProperties.class)
public class DingtalkAutoConfiguration {


    @Bean
    @Primary
    public DingtalkConfig getDingtalkConfig(DingtalkProperties properties) {
        DingtalkConfig config = new DingtalkConfig();
        config.setDomain(properties.getDomain());
        config.setAppkey(properties.getAppkey());
        config.setAppsecret(properties.getAppsecret());
        config.setIsLog(properties.getLog());
        if(config.getIsLog()){
            log.info("自动装配 -> 钉钉组件");
        }
        return config;
    }
}
