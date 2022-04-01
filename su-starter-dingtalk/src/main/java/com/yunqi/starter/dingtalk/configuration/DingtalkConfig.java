package com.yunqi.starter.dingtalk.configuration;

import lombok.Data;

/**
 * 配置类 Model
 * <p>
 * 你可以通过yml、properties、java代码等形式配置本类参数
 * Created by @author JsckChin on 2022/4/1
 */
@Data
public class DingtalkConfig {

    private static final long serialVersionUID = 1L;

    /** API接口地址 */
    private String domain = "";

    /** 应用的唯一标识key */
    private String appkey = "";

    /** 应用的密钥 */
    private String appsecret = "";

    /** 是否打印操作日志 */
    private Boolean isLog = true;

}
