package com.yunqi.starter.redis.configuration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by @author JsckChin on 2022/2/2
 */
@Configuration
@ConditionalOnExpression("${su.redis.enabled:true}")
@EnableConfigurationProperties(RedisProperties.class)
@AutoConfigureAfter(org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration.class)
public class RedisAutoConfiguration {

    private RedisProperties properties;

    private RedisAutoConfiguration(RedisProperties properties){
        this.properties = properties;
    }

    @Bean
    public JedisPool JedisPoolFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        return new JedisPool(poolConfig,
                properties.getHost(),
                properties.getPort(),
                properties.getTimeout(),
                properties.getPassword());
    }


}
