package com.yunqi.starter.jdbc.configuration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * Druid Web 网络统计及健康
 * Created by @author CHQ on 2022/2/1
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnExpression("${su.druid.web-stat-filter.enabled:true}")
@AutoConfigureAfter(DruidDataSourceAutoConfigure.class)
public class DruidWebStatFilterConfiguration {

    /**
     * 配置一个web监控的filter
     * @return  webStatFilter
     */
    @Bean
    public FilterRegistrationBean<WebStatFilter> webStatFilter(){
        FilterRegistrationBean<WebStatFilter> bean = new FilterRegistrationBean<>();
        // 设置Druid过滤器
        bean.setFilter(new WebStatFilter());
        // 添加过滤规则
        bean.setUrlPatterns(Collections.singletonList("/*"));
        // 忽略过滤格式
        bean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return bean;
    }
}
