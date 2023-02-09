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

    /** 邮件服务器地址 */
    private String hostName = "smtp.qiye.aliyun.com";

    /** 邮件服务器端口 */
    private String smtpPort = "465";

    /** 邮箱账号 */
    private String userName = "notice@fir7.com";

    /** 邮箱密码 */
    private String password = "Ng1259900";

    /** 邮件服务器是否HTTPS */
    private boolean ssl = true;

    /** 邮箱用户名 */
    private String from = "notice@fir7.com";

    /** 邮箱编码 */
    private String charset = "UTF-8";

}
