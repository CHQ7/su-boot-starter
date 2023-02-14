package com.yunqi.starter.security.configuration;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.spring.SaBeanRegister;
import cn.dev33.satoken.strategy.SaStrategy;
import com.yunqi.starter.common.json.Json;
import com.yunqi.starter.security.provider.IAuthProvider;
import com.yunqi.starter.security.provider.impl.IAuthProviderDefaultImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
@Slf4j
@Configuration
@ConditionalOnClass(SaTokenConfig.class)
@ConditionalOnExpression("${su.security.enabled:true}")
@EnableConfigurationProperties(SecurityProperties.class)
@AutoConfigureAfter({SaBeanRegister.class})
public class SecurityAutoConfiguration implements WebMvcConfigurer {

    // 安全属性对象
    private final SecurityProperties properties;


    public SecurityAutoConfiguration(SecurityProperties properties) {
        this.properties = properties;
    }

    /**
     * 创建 SaTokenConfig Bean，并将 SecurityProperties 的值复制到 SaTokenConfig 中
     * @return SaTokenConfig Bean
     */
    @Bean
    @Primary
    public SaTokenConfig saTokenConfig() {
        SaTokenConfig config = new SaTokenConfig();
        BeanUtils.copyProperties(properties, config);
        if(config.getIsLog()){
            log.info("自动装配 -> 权限组件");
            log.info("打印 -> su.security 配置:\n{}", Json.toJson(config));
        }
        return config;
    }

    /**
     * 创建 iAuthProvider Bean, 如果开发者没有实现 IAuthProvider 接口，则使用此默认实现
     * @return IAuthProvider Bean
     */
    @Bean
    @ConditionalOnMissingBean(IAuthProvider.class)
    public IAuthProvider iAuthProvider() {
        if(properties.getIsLog()){
            log.info("打印 -> 开发者未实现 IAuthProvider 接口，则使用此默认接口");
        }
        return new IAuthProviderDefaultImpl();
    }

    /** 注册Sa-Token的注解拦截器，打开注解式鉴权功能 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
    }

    /** 重写Sa-Token的注解处理器，增加注解合并功能 */
    @Autowired
    public void rewriteSaStrategy() {
        SaStrategy.me.getAnnotation = AnnotatedElementUtils::getMergedAnnotation;
    }

}
