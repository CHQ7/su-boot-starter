package com.yunqi.starter.rabbit.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by @author CHQ on 2022/2/16
 */
@Getter
@Setter
@ConfigurationProperties(prefix = RabbitProperties.PREFIX)
public class RabbitProperties {

    public static final String PREFIX = "spring.rabbitmq";

    /** 是否开启 */
    boolean enabled = true;

    /** RabbitMQ主机。如果设置了地址，则忽略 */
    private String host = "127.0.0.1";

    /** RabbitMQ端口。如果设置了地址，则忽略。默认为5672 */
    private Integer port = 5672;

    /** 登录用户向代理进行身份验证。 */
    private String username = "root";

    /** 登录以根据代理进行身份验证。 */
    private String password = "root";

    /** 是否打印操作日志 */
    private Boolean isLog = false;
}
