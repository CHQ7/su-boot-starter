package com.yunqi.starter.log.configuration;

import com.yunqi.starter.log.aop.SLogAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author JsckChin on 2022/2/17
 */
@Configuration
@ConditionalOnExpression("${su.log.enabled:true}")
@EnableConfigurationProperties(LogProperties.class)
public class LogAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public SLogAspect sLogAspect(LogProperties properties) {
        return new SLogAspect(properties);
    }

}
