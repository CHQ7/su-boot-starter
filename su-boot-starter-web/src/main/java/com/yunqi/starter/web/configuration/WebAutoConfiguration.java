package com.yunqi.starter.web.configuration;

import com.yunqi.starter.common.constant.GlobalConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author CHQ on 2022/2/24
 */
@Slf4j
@Configuration
@ConditionalOnExpression("${su.web.enabled:true}")
@EnableConfigurationProperties(WebProperties.class)
public class WebAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public WebMvcConfig getWebConfig(WebProperties properties) {
        if(properties.getLog()){
            log.info("自动装配 -> WEB组件");
        }

        GlobalConstant.DEFAULT_SYSTEM_ROOT = properties.getRoot();
        return new WebMvcConfig();
    }

}
