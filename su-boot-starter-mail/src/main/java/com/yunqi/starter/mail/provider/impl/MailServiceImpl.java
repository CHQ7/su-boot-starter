package com.yunqi.starter.mail.provider.impl;

import com.yunqi.starter.common.lang.Strings;
import com.yunqi.starter.mail.configuration.MailProperties;
import com.yunqi.starter.mail.configuration.MailSenderFactory;
import com.yunqi.starter.mail.model.Email;
import com.yunqi.starter.mail.provider.IMailProvider;
import lombok.extern.slf4j.Slf4j;
import org.nutz.lang.Lang;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * Created by @author CHQ on 2022/1/29
 */
@Slf4j
public class MailServiceImpl implements IMailProvider {

    private final MailProperties properties;
    private final MailSenderFactory mailSenderFactory;

    public MailServiceImpl(MailProperties properties){
        this.properties = properties;
        this.mailSenderFactory = new MailSenderFactory(properties);
    }

    @Override
    public void sendText(Email email) {
        sendEmail(email, false);
    }

    @Override
    public void sendHtml(Email email) {
        sendEmail(email, true);
    }

    /**
     * 发送邮件
     * @param email 邮件信息
     * @param html  是否支持HTML邮件
     */
    private void sendEmail(Email email, boolean html){
        JavaMailSenderImpl javaMailSender = mailSenderFactory.createJavaMailSender();
        MimeMessage message = javaMailSender.createMimeMessage();
        prepareMimeMessage(message, email, html);
        javaMailSender.send(message);
    }

    /**
     * 准备消息
     * @param mimeMessage   电子邮件消息
     * @param email         邮件信息
     * @param html          是否支持HTML邮件
     */
    private void prepareMimeMessage(MimeMessage mimeMessage, Email email, boolean html) {
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
