package com.yunqi.starter.web.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by @author CHQ on 2022/2/24
 */
@Data
@ConfigurationProperties(prefix = WebProperties.PREFIX)
public class WebProperties {

    public static final String PREFIX = "su.web";

    /** 是否开启 */
    boolean enabled = true;

    /** 项目路径 */
    private String root;

    /** 是否打印操作日志 */
    private Boolean log = false;

}
