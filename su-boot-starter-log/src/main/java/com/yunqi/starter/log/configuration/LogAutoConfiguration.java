package com.yunqi.starter.log.configuration;

import com.yunqi.starter.log.aop.SLogAspect;
import com.yunqi.starter.log.provider.ILogRecordProvider;
import com.yunqi.starter.log.provider.impl.LogRecordProviderDefaultImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注解日志自动配置类
 * Created by @author CHQ on 2022/2/17
 */
@Slf4j
@Configuration
@ConditionalOnExpression("${su.log.enabled:true}")
@EnableConfigurationProperties(LogProperties.class)
public class LogAutoConfiguration {

    private final LogProperties properties;


    public LogAutoConfiguration(LogProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public SLogAspect sLogAspect() {
        return new SLogAspect();
    }

    @Bean
    @ConditionalOnMissingBean(ILogRecordProvider.class)
    public ILogRecordProvider logRecordProvider() {
        if(properties.getLog()){
            log.info("打印 -> 开发者未实现 ILogRecordProvider 接口，则使用此默认接口");
        }
        return new LogRecordProviderDefaultImpl();
    }
}
