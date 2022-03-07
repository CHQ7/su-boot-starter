package com.yunqi.starter.mail.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by @author JsckChin on 2022/1/29
 */
@Data
@ConfigurationProperties(prefix = "su.mail")
public class MailProperties {

    /** 是否开启 */
    boolean enabled = true;

    private String hostName = "smtp.qiye.aliyun.com";

    private String smtpPort = "465";

    private String userName = "notice@fir7.com";

    private String password = "Ng1259900";

    private boolean ssl = true;

    private String from = "notice@fir7.com";

    private String charset = "UTF-8";

}
