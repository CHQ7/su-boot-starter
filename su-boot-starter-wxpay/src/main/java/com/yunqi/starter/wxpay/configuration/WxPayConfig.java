package com.yunqi.starter.wxpay.configuration;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 微信支付常用配置
 * Created by @author CHQ on 2022/9/18
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WxPayConfig implements Serializable {

    private static final long serialVersionUID = -9044503427692786302L;

    /**
     * API接口地址
     */
    private String domain = "https://api.mch.weixin.qq.com";
    /**
     * 应用编号
     */
    private String appId;
    /**
     * 商户号
     */
    private String mchId;
    /**
     * 商户平台「API安全」中的 API 密钥
     */
    private String apiKey;
    /**
     * 商户平台「API安全」中的 APIv3 密钥
     */
    private String apiKey3;
    /**
     * 回调域名中会使用此参数
     */
    private String callbackDomain;
    /**
     * API 证书中的 p12
     */
    private String certPath;
    /**
     * API 证书中的 key.pem
     */
    private String keyPemPath;
    /**
     * API 证书中的 cert.pem
     */
    private String certPemPath;
    /**
     * 平台证书
     */
    private String platformCertPath;
    /**
     * 其他附加参数
     */
    private Object exParams;
    /**
     * 是否打印操作日志
     */
    private Boolean isLog = true;
}
