package com.yunqi.starter.mail.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by @author JsckChin on 2022/1/29
 */
@Data
@ConfigurationProperties(prefix = "su.mail")
public class MailProperties {

    boolean enabled = true;

    String hostName = "smtp.qiye.aliyun.com";

    String smtpPort = "465";

    String userName = "notice@fir7.com";

    String password = "Ng1259900";

    boolean ssl = true;

    String from = "notice@fir7.com";

    String charset = "UTF-8";

}
