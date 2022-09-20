package com.yunqi.starter.wx.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by @author CHQ on 2022/2/24
 */
@Data
@ConfigurationProperties(prefix = WxProperties.PREFIX)
public class WxProperties {

    public static final String PREFIX = "su.wx";

    /** 是否开启 */
    boolean enabled = true;

    /** 应用的唯一标识key */
    private String appkey = "";

    /** 应用的密钥 */
    private String appsecret = "";

    /** 是否打印操作日志 */
    private Boolean log = false;

}
