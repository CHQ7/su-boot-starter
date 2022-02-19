package com.yunqi.starter.mail.service.impl;

import com.yunqi.starter.common.lang.Lang;
import com.yunqi.starter.common.lang.Strings;
import com.yunqi.starter.mail.configuration.MailProperties;
import com.yunqi.starter.mail.entity.Email;
import com.yunqi.starter.mail.service.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Created by @author JsckChin on 2022/1/29
 */
@Slf4j
public class MailServiceImpl implements IMailService {

    private MailProperties properties;

    public MailServiceImpl(MailProperties properties){
        this.properties = properties;
    }

    @Override
    public void send(Email email) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(properties.getHostName());
        javaMailSender.setPort(Integer.parseInt(properties.getSmtpPort()));
        javaMailSender.setUsername(properties.getUserName());
        javaMailSender.setPassword(properties.getPassword());
        javaMailSender.setDefaultEncoding(properties.getCharset());
        if(properties.isSsl()){
            Properties prop = new Properties();
            prop.put("mail.smtp.ssl.enable", properties.isSsl());
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            javaMailSender.setJavaMailProperties(prop);
        }
        MimeMessage message = javaMailSender.createMimeMessage();
        buildEmail(message, email, false);
        javaMailSender.send(message);
    }

    @Override
    public void sendHtml(Email email) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(properties.getHostName());
        javaMailSender.setPort(Integer.parseInt(properties.getSmtpPort()));
        javaMailSender.setUsername(properties.getUserName());
        javaMailSender.setPassword(properties.getPassword());
        javaMailSender.setDefaultEncoding(properties.getCharset());
        if(properties.isSsl()){
            Properties prop = new Properties();
            prop.put("mail.smtp.ssl.enable", properties.isSsl());
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            javaMailSender.setJavaMailProperties(prop);
        }
        MimeMessage message = javaMailSender.createMimeMessage();
        buildEmail(message, email, true);
        javaMailSender.send(message);
    }

    private void buildEmail(MimeMessage mimeMessage, Email email, boolean html) {
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(properties.getUserName());
            if (Strings.isNotBlank(properties.getFrom())) {
                helper.setFrom(properties.getUserName(), properties.getFrom());
            }
            helper.setTo(email.getTo());
            helper.setSubject(email.getSubject());
            helper.setText(email.getContent(), html);
            if (Lang.isNotEmpty(email.getCc())) {
                helper.setCc(email.getCc());
            }
            // 附件
            if(Strings.isNotBlank(email.getFilePath())){
                FileSystemResource file = new FileSystemResource(new File(email.getFilePath()));
                String fileName = email.getFilePath().substring(email.getFilePath().lastIndexOf(File.separator));
                helper.addAttachment(fileName, file);
            }

        } catch (UnsupportedEncodingException e) {
            log.error("不支持字符编码", e);
            throw new RuntimeException(e);
        } catch (MessagingException e){
            throw new RuntimeException(e);
        }
    }


}
