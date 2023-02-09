package com.yunqi.starter.mail.configuration;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * 配置文件的构建工厂类
 * Created by @author CHQ on 2023/2/9
 */
public class MailSenderFactory {

    private final MailProperties properties;

    public MailSenderFactory(MailProperties properties) {
        this.properties = properties;
    }

    /**
     * 获取邮件发送对象
     * @return 邮件发送对象
     */
    public JavaMailSenderImpl createJavaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(properties.getHostName());
        javaMailSender.setPort(Integer.parseInt(properties.getSmtpPort()));
        javaMailSender.setUsername(properties.getUserName());
        javaMailSender.setPassword(properties.getPassword());
        javaMailSender.setDefaultEncoding(properties.getCharset());

        if (properties.isSsl()) {
            Properties prop = new Properties();
            prop.put("mail.smtp.ssl.enable", properties.isSsl());
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            javaMailSender.setJavaMailProperties(prop);
        }
        return javaMailSender;
    }
}
