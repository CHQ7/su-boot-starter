package com.yunqi.starter.wx.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by @author JsckChin on 2022/2/24
 */
@Data
@ConfigurationProperties(prefix = "su.wx")
public class WxProperties {

    /** 是否开启 */
    boolean enabled = true;

    /** 小程序ID */
    private String appid;

    /** 小程序密钥 */
    private String appsecret;
}
