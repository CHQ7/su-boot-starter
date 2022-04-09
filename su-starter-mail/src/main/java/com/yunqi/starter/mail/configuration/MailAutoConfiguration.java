package com.yunqi.starter.mail.configuration;

import com.yunqi.starter.mail.provider.IMailProvider;
import com.yunqi.starter.mail.provider.impl.MailServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author CHQ on 2022/1/29
 */
@Configuration
@ConditionalOnClass({MailServiceImpl.class})
@ConditionalOnExpression("${su.mail.enabled:true}")
@EnableConfigurationProperties(MailProperties.class)
public class MailAutoConfiguration {

    @Bean
    public IMailProvider iMailService(MailProperties properties){
        return new MailServiceImpl(properties);
    }
}
