package com.yunqi.starter.security.configuration;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.spring.SaBeanRegister;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Created by @author CHQ on 2022/2/16
 */
@Configuration
@ConditionalOnClass(SaTokenConfig.class)
@ConditionalOnExpression("${su.security.user.enabled:true}")
@EnableConfigurationProperties(UserSecurityProperties.class)
@AutoConfigureAfter({SaBeanRegister.class})
public class UserSecurityAutoConfiguration implements WebMvcConfigurer {

    // 安全属性对象
    private final UserSecurityProperties properties;
    public UserSecurityAutoConfiguration(UserSecurityProperties properties) {
        this.properties = properties;
    }



}
