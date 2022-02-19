package com.yunqi.starter.security.configuration;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.dev33.satoken.spring.SaBeanRegister;
import com.yunqi.starter.security.handler.SecurityGlobalException;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Created by @author JsckChin on 2022/2/16
 */
@Configuration
@ConditionalOnClass(SaTokenConfig.class)
@ConditionalOnExpression("${su.security.enabled:true}")
@EnableConfigurationProperties(SecurityProperties.class)
@AutoConfigureAfter({SaBeanRegister.class})
public class SecurityAutoConfiguration implements WebMvcConfigurer {

    @Bean
    @Primary
    public SaTokenConfig getSaTokenConfigPrimary(SecurityProperties properties) {
        SaTokenConfig config = new SaTokenConfig();
        // token名称 (同时也是cookie名称)
        config.setTokenName(properties.getTokenName());
        // token有效期，单位s 默认30天
        config.setTimeout(properties.getTimeout());
        // token临时有效期 (指定时间内无操作就视为token过期) 单位: 秒
        config.setActivityTimeout(properties.getActivityTimeout());
        // 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)
        config.setIsConcurrent(properties.getIsConcurrent());
        // 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)
        config.setIsShare(properties.getIsShare());
        // token风格
        config.setTokenStyle(properties.getTokenStyle());
        // 是否输出操作日志
        config.setIsLog(properties.getIsLog());
        // 是否在初始化配置时打印版本字符画
        config.setIsPrint(properties.getIsPrint());
        return config;
    }

    /**
     * 注册Sa-Token的注解拦截器，打开注解式鉴权功能
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册注解拦截器，并排除不需要注解鉴权的接口地址 (与登录拦截器无关)
        registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns("/**");
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityGlobalException securityGlobalException() {
        return new SecurityGlobalException();
    }
}
