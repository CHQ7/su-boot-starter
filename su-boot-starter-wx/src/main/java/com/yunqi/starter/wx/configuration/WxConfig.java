package com.yunqi.starter.wx.configuration;

import lombok.Data;

/**
 * 配置类 Model
 * <p>
 * 你可以通过yml、properties、java代码等形式配置本类参数
 * Created by @author CHQ on 2022/6/19
 */
@Data
public class WxConfig {

    private static final long serialVersionUID = 1L;

    /** API接口地址 */
    private String domain = "https://api.weixin.qq.com";

    /** 应用的唯一标识key */
    private String appkey = "";

    /** 应用的密钥 */
    private String appsecret = "";

    /** 是否打印操作日志 */
    private Boolean isLog = true;

}
