package com.yunqi.starter.mail.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by @author CHQ on 2022/1/29
 */
@Getter
@Setter
@ConfigurationProperties(prefix = MailProperties.PREFIX)
public class MailProperties {

    public static final String PREFIX = "su.mail";

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
