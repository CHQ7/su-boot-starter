package com.yunqi.starter.log.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 系统日志
 * Created by @author CHQ on 2021/12/19
 */
@Data
@Accessors(chain = true)
public class SLogRecord {

    /** 日志标签 */
    private String tag;

    /** 业务类型 */
    private String msg;

    /** 请求地址 */
    private String url;

    /** 请求方式 */
    private String method;

    /** 执行类 */
    private String src;

    /** IP */
    private String ip;

    /** IP地址 */
    private String location;

    /** 浏览器类型 */
    private String browser;

    /** 操作系统 */
    private String os;

    /** 请求参数 */
    private String param;

    /** 执行结果 */
    private String result;

    /** 处理耗时,单位：ms */
    private Long executeTime;

    /** 是否调用成功 */
    private Boolean success;

    /** 操作人ID */
    private String operateUserId;

    /** 操作人 */
    private String operateUserName;

}
