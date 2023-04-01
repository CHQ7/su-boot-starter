package com.yunqi.starter.web.configuration;

import com.yunqi.starter.common.constant.GlobalConstant;
import com.yunqi.starter.web.filter.TraceIdFilter;
import com.yunqi.starter.web.pool.ThreadPoolExecutorMdcWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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


    @Bean
    public FilterRegistrationBean<TraceIdFilter> traceIdFilterRegistrationBean() {
        FilterRegistrationBean<TraceIdFilter> registrationBean = new FilterRegistrationBean<>();
        TraceIdFilter traceIdFilter = new TraceIdFilter();
        registrationBean.setFilter(traceIdFilter);
        registrationBean.setOrder(1);
        return registrationBean;
    }

    /** 核心线程池大小 */
    private final int corePoolSize = Runtime.getRuntime().availableProcessors() + 1;

    /** 最大可创建的线程数 */
    private final int maxPoolSize = corePoolSize * 2;

    /** 队列最大长度 */
    private static final int queueCapacity = 1000;

    /** 线程池维护线程所允许的空闲时间 */
    private static final int keepAliveSeconds = 300;

    @Bean
    public ThreadPoolExecutorMdcWrapper threadPoolExecutorMdcWrapper() {
        ThreadPoolExecutorMdcWrapper executorMdcWrapper = new ThreadPoolExecutorMdcWrapper(
                corePoolSize,
                maxPoolSize,
                keepAliveSeconds,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueCapacity),
                r -> new Thread(r, "log-"),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        return executorMdcWrapper;
    }

}
