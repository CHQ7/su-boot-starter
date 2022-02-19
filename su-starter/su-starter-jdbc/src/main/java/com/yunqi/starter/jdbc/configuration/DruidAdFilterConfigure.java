package com.yunqi.starter.jdbc.configuration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.util.Utils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import java.io.IOException;

/**
 * 移除Druid广告
 * Created by @author JsckChin on 2022/2/1
 */
@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(DruidDataSourceAutoConfigure.class)
@EnableConfigurationProperties({DruidStatProperties.class})
@ConditionalOnProperty(name = "spring.datasource.druid.stat-view-servlet.enabled", havingValue = "true", matchIfMissing = true)
public class DruidAdFilterConfigure {

    /**
     * 过滤Druid底部广告
     * @param properties DruidStatProperties
     * @return           注册过滤器,过滤druid底部广告
     * @throws IOException  IO异常
     */
    @Bean
    public FilterRegistrationBean<RemoveAdFilter> druidAdFilter(DruidStatProperties properties) throws IOException {
        // 获取web监控页面的参数
        DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
        // 提取common.js的配置路径
        String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
        String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");
        // 获取common.js 查找带有广告的common.js全路径
        String text = Utils.readFromResource("support/http/resources/js/common.js");
        // 屏蔽 this.buildFooter(); 不构建广告
        final String newJs = text.replace("this.buildFooter();", "//this.buildFooter();");
        FilterRegistrationBean<RemoveAdFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new RemoveAdFilter(newJs));
        bean.addUrlPatterns(commonJsPattern);
        return bean;
    }


    /**
     * 删除druid的广告过滤器
     * @author BBF
     */
    private static class RemoveAdFilter implements Filter {

        private final String newJs;

        public RemoveAdFilter(String newJS) {
            this.newJs = newJS;
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            chain.doFilter(request, response);
            // 重置缓冲区，响应头不会被重置
            response.resetBuffer();
            response.getWriter().write(newJs);
        }
    }

}
