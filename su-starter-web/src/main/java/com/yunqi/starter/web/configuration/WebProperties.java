package com.yunqi.starter.web.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by @author JsckChin on 2022/2/24
 */
@Data
@ConfigurationProperties(prefix = "su.web")
public class WebProperties {

    /** 是否开启 */
    boolean enabled = true;

}
