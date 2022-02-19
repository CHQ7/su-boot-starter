package com.yunqi.starter.log.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by @author JsckChin on 2022/2/16
 */
@Data
@ConfigurationProperties(prefix = "su.log")
public class LogProperties {

    /** 是否开启 */
    boolean enabled = true;
}
