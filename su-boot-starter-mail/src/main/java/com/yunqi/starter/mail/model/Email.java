package com.yunqi.starter.mail.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 邮件信息
 * Created by @author CHQ on 2022/1/29
 */
@Data
@Accessors(chain = true)
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 接收人邮箱
     */
    private String to;

    /**
     * 主题
     */
    private String subject;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 发送内容
     */
    private String[] cc;

    /**
     * 附件地址
     */
    private String filePath;

    /**
     * 模板
     */
    private String template;

}
