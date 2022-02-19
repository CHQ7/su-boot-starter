package com.yunqi.starter.redis.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by @author JsckChin on 2022/2/2
 */
@Data
@ConfigurationProperties(prefix = "su.redis")
public class RedisProperties {

    /**
     * 是否启动
     */
    boolean enabled = true;

    /**
     * 连接工厂使用的数据库索引
     */
    private int database = 0;

    /**
     * 连接URL。覆盖主机、端口和密码。用户被忽略。例子：
     * redis://user:password@example.com:6379
     */
    private String url;

    /**
     * Redis服务器主机
     */
    private String host = "127.0.0.1";

    /**
     * Redis服务器端口.
     */
    private int port = 6379;

    /**
     * redis服务器的登录用户名.
     */
    private String username;

    /**
     * redis服务器的登录密码.
     */
    private String password;

    /**
     * 是否启用SSL支持.
     */
    private boolean ssl;

    /**
     * 读取超时.
     */
    private int timeout=2000;

    /**
     * 连接超时.
     */
    private int connectTimeout;



}
