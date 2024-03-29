package com.yunqi.starter.mail.provider;

import com.yunqi.starter.mail.model.Email;

/**
 * Created by @author CHQ on 2022/1/29
 */
public interface IMailProvider {

    /**
     * 发送文本邮件
     * @param email 邮件信息
     */
    void sendText(Email email);

    /**
     * 发送HTML邮件
     * @param email 邮件信息
     */
    void sendHtml(Email email);


}
