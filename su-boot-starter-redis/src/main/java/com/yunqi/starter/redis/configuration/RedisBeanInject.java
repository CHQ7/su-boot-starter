package com.yunqi.starter.redis.configuration;

import com.yunqi.starter.redis.spi.RedisCaches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 注入所需要的Bean
 * Created by @author CHQ on 2022/8/22
 */
public class RedisBeanInject {

    /**
     * 注入配置Bean
     *
     * @param redisCacheTemplate 配置对象
     */
    @Autowired(required = false)
    public void setConfig(RedisTemplate<String, Object> redisCacheTemplate) {
        RedisCaches.setRedisTemplate(redisCacheTemplate);
    }
}
