package com.yunqi.starter.wxpay.configuration;

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
@ConditionalOnExpression("${su.wxpay.enabled:true}")
@EnableConfigurationProperties(WxPayProperties.class)
public class WxPayAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public WxPayConfig getWxPayConfig(WxPayProperties properties) {
        if(properties.getLog()){
            log.info("自动装配 -> 微信支付组件");
        }
        return new WxPayConfig()
                .setAppId(properties.getAppId())
                .setMchId(properties.getMchId())
                .setApiKey(properties.getApiKey())
                .setApiKey3(properties.getApiKey3())
                .setCallbackDomain(properties.getCallbackDomain())
                .setCertPath(properties.getCertPath())
                .setKeyPemPath(properties.getKeyPemPath())
                .setCertPemPath(properties.getCertPemPath())
                .setExParams(properties.getExParams())
                .setIsLog(properties.getLog());
    }
}
