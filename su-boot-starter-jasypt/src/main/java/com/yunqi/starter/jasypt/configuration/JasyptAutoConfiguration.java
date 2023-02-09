package com.yunqi.starter.jasypt.configuration;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by @author CHQ on 2022/1/29
 */
@Configuration
@ConditionalOnExpression("${su.jasypt.enabled:true}")
@AutoConfigureAfter({StringEncryptor.class})
@EnableConfigurationProperties(JasyptProperties.class)
public class JasyptAutoConfiguration {

    @Bean("jasyptStringEncryptor")
    @Primary
    public StringEncryptor stringEncryptor(JasyptProperties properties) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        // Jasypt 加密密钥
        config.setPassword(properties.getPassword());
        // Jasypt 加密算法
        config.setAlgorithm(properties.getAlgorithm());
        // Jasypt 密钥获取次数
        config.setKeyObtentionIterations(properties.getKeyObtentionIterations());
        // Jasypt 密钥池大小
        config.setPoolSize(properties.getPoolSize());
        // Jasypt 加密提供者名称
        config.setProviderName(properties.getProviderName());
        // Jasypt Salt生成器类名
        config.setSaltGeneratorClassName(properties.getSaltGeneratorClassName());
        // Jasypt IV生成器类名
        config.setIvGeneratorClassName(properties.getIvGeneratorClassName());
        // Jasypt 加密字符串输出类型
        config.setStringOutputType(properties.getStringOutputType());
        encryptor.setConfig(config);
        return encryptor;
    }

}
