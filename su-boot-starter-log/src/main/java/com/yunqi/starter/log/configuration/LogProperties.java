package com.yunqi.starter.log.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 注解日志配置属性
 * Created by @author CHQ on 2022/2/16
 */
@Getter
@Setter
@ConfigurationProperties(prefix = LogProperties.PREFIX)
public class LogProperties {

    public static final String PREFIX = "su.log";

    /** 是否开启 */
    boolean enabled = true;
}
