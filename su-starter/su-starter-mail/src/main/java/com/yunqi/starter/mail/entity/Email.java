package com.yunqi.starter.mail.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by @author JsckChin on 2022/1/29
 */
@Data
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
