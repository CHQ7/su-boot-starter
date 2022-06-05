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
 * Created by @author CHQ on 2022/2/1
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnExpression("${su.druid.stat-view-servlet.enabled:true}")
@AutoConfigureAfter(DruidDataSourceAutoConfigure.class)
@EnableConfigurationProperties(DruidDataSourceProperties.class)
public class DruidWebStatServletConfiguration {

    /**
     * 配置druid监控
     * @return  statViewServlet
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet(DruidDataSourceProperties properties){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

        Map<String, String> map = new HashMap<>();
        // IP黑名单 (存在共同时，deny优先于allow)
        if(!StringUtils.isEmpty(properties.getStatViewServlet().getDeny())){
            map.put("deny", properties.getStatViewServlet().getDeny());
        }
        // IP白名单 (没有配置或者为空，则允许所有访问) 127.0.0.1 只允许本机访问
        if(!StringUtils.isEmpty(properties.getStatViewServlet().getAllow())){
            map.put("allow", properties.getStatViewServlet().getAllow());
        }

        //设置控制台登录的用户名和密码
        map.put("loginUsername", properties.getStatViewServlet().getLoginUsername());
        map.put("loginPassword", properties.getStatViewServlet().getLoginPassword());

        //是否能够重置数据
        map.put("resetEnable", properties.getStatViewServlet().getResetEnable());

        bean.setInitParameters(map);
        return bean;
    }
}
