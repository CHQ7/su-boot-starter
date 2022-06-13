package com.yunqi.starter.web.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author CHQ on 2022/2/24
 */
@Configuration
@ConditionalOnExpression("${su.web.enabled:true}")
@EnableConfigurationProperties(WebProperties.class)
public class WebAutoConfiguration {


}
