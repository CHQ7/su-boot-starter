package com.yunqi.starter.web.configuration;

import com.yunqi.starter.common.constant.GlobalConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 通用配置 WebMvc
 * Created by @author CHQ on 2022/10/27
 */
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /* 配置虚拟路径 -> 本地文件上传路径 */
        registry.addResourceHandler("/**")
                .addResourceLocations("file:" + GlobalConstant.DEFAULT_SYSTEM_ROOT + "/");
    }
}
