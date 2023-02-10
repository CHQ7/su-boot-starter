package com.yunqi.starter.jdbc.configuration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.util.StringUtils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * DruidWebStatServlet配置类，用于配置druid的状态监控页面。
 * Created by @author CHQ on 2022/2/1
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnExpression("${su.druid.stat-view-servlet.enabled:true}")
@AutoConfigureAfter(DruidDataSourceAutoConfigure.class)
@EnableConfigurationProperties(DruidProperties.class)
public class DruidWebStatServletConfiguration {

    /**
     * 配置druid监控
     * @param properties druid配置参数
     * @return  ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet(DruidProperties properties){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

        Map<String, String> initParameters  = new HashMap<>();
        // IP黑名单 (存在共同时，deny优先于allow)
        if(!StringUtils.isEmpty(properties.getStatViewServlet().getDeny())){
            initParameters .put("deny", properties.getStatViewServlet().getDeny());
        }
        // IP白名单 (没有配置或者为空，则允许所有访问) 127.0.0.1 只允许本机访问
        if(!StringUtils.isEmpty(properties.getStatViewServlet().getAllow())){
            initParameters .put("allow", properties.getStatViewServlet().getAllow());
        }

        // 设置控制台登录的用户名和密码
        // 是否能够重置数据
        initParameters .put("loginUsername", properties.getStatViewServlet().getLoginUsername());
        initParameters .put("loginPassword", properties.getStatViewServlet().getLoginPassword());
        initParameters .put("resetEnable", properties.getStatViewServlet().getResetEnable());

        bean.setInitParameters(initParameters);
        return bean;
    }
}
