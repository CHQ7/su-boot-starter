package com.yunqi.starter.mail.service;

import com.yunqi.starter.mail.entity.Email;

/**
 * Created by @author JsckChin on 2022/1/29
 */
public interface IMailService {

    /**
     * 发送文本邮件
     * @param email 邮件信息
     */
    void send(Email email);

    /**
     * 发送HTML邮件
     * @param email 邮件信息
     */
    void sendHtml(Email email);

}
