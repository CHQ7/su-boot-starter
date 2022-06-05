package com.yunqi.starter.web.configuration;

import com.yunqi.starter.web.handler.GlobalException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author CHQ on 2022/2/24
 */
@Configuration
@ConditionalOnExpression("${su.web.enabled:true}")
@EnableConfigurationProperties(WebProperties.class)
public class WebAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public GlobalException globalException() {
        return new GlobalException();
    }
}
