package com.yunqi.starter.quartz.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by @author CHQ on 2022/2/2
 */
@Data
@ConfigurationProperties(prefix = "su.quartz")
public class QuartzProperties {

    /** 是否开启 */
    boolean enabled = true;

    /** 时区 */
    private String timeZone = "Asia/Shanghai";

    /** 外部配置 */
    private Map<String, String> properties = new HashMap<>();

}
