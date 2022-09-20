package com.yunqi.starter.wxpay.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信支付v3配置
 * Created by @author CHQ on 2022/9/18
 */
@ConfigurationProperties(prefix = WxPayProperties.PREFIX)
public class WxPayProperties {

    public static final String PREFIX = "su.wxpay";

    /** 是否开启 */
    boolean enabled = true;

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

    /** 是否打印操作日志 */
    private Boolean log = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey3() {
        return apiKey3;
    }

    public void setApiKey3(String apiKey3) {
        this.apiKey3 = apiKey3;
    }

    public String getCallbackDomain() {
        return callbackDomain;
    }

    public void setCallbackDomain(String callbackDomain) {
        this.callbackDomain = callbackDomain;
    }

    public String getCertPath() {
        return certPath;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }

    public String getKeyPemPath() {
        return keyPemPath;
    }

    public void setKeyPemPath(String keyPemPath) {
        this.keyPemPath = keyPemPath;
    }

    public String getCertPemPath() {
        return certPemPath;
    }

    public void setCertPemPath(String certPemPath) {
        this.certPemPath = certPemPath;
    }

    public String getPlatformCertPath() {
        return platformCertPath;
    }

    public void setPlatformCertPath(String platformCertPath) {
        this.platformCertPath = platformCertPath;
    }

    public Object getExParams() {
        return exParams;
    }

    public void setExParams(Object exParams) {
        this.exParams = exParams;
    }

    public Boolean getLog() {
        return log;
    }

    public void setLog(Boolean log) {
        this.log = log;
    }
}
