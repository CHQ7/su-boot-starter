package com.yunqi.starter.dingtalk.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by @author CHQ on 2022/4/1
 */
@Data
@ConfigurationProperties(prefix = "su.dingtalk")
public class DingtalkProperties {

    /** 是否开启 */
    boolean enabled = true;

    /** API接口地址 */
    private String domain = "https://oapi.dingtalk.com";

    /** 应用的唯一标识key */
    private String appkey = "";

    /** 应用的密钥 */
    private String appsecret = "";

    /** 是否打印操作日志 */
    private Boolean log = false;
}
