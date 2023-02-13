package com.yunqi.starter.security.configuration;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.spring.SaBeanRegister;
import cn.dev33.satoken.strategy.SaStrategy;
import com.yunqi.starter.security.spi.UserSecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Created by @author CHQ on 2022/2/16
 */
@Configuration
@ConditionalOnClass(SaTokenConfig.class)
@ConditionalOnExpression("${su.security.enabled:true}")
@EnableConfigurationProperties(UserSecurityProperties.class)
@AutoConfigureAfter({SaBeanRegister.class})
public class UserSecurityAutoConfiguration implements WebMvcConfigurer {

    // 安全属性对象
    private final UserSecurityProperties properties;
    public UserSecurityAutoConfiguration(UserSecurityProperties properties) {
        this.properties = properties;
    }

    /**
     * 创建 SaTokenConfig Bean，并将 SecurityProperties 的值复制到 SaTokenConfig 中
     * @return SaTokenConfig Bean
     */
    @Bean
    @Qualifier("userTokenConfig")
    public UserSecurityUtil userSecurityUtil() {
        SaTokenConfig config = new SaTokenConfig();
        BeanUtils.copyProperties(properties, config);
        return new UserSecurityUtil(config);
    }

}