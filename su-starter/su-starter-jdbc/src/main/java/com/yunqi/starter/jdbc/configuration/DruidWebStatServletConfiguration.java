package com.yunqi.starter.jdbc.configuration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by @author JsckChin on 2022/2/1
 */
@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(DruidDataSourceAutoConfigure.class)
@EnableConfigurationProperties({DruidStatProperties.class})
@ConditionalOnProperty(name = "spring.datasource.druid.stat-view-servlet.enabled", havingValue = "true", matchIfMissing = true)
public class DruidWebStatServletConfiguration {

    /**
     * 配置druid监控
     * @return  statViewServlet
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

        Map<String, String> map = new HashMap<>();
        // IP黑名单 (存在共同时，deny优先于allow)
        // map.put("deny", "127.0.0.1");

        // IP白名单 (没有配置或者为空，则允许所有访问) 127.0.0.1 只允许本机访问
        map.put("allow", "127.0.0.1");

        //设置控制台登录的用户名和密码
        map.put("loginUsername", "root");
        map.put("loginPassword", "root");

        //是否能够重置数据
        map.put("resetEnable", "false");

        bean.setInitParameters(map);
        return bean;
    }
}
