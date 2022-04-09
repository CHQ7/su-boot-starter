package com.yunqi.starter.jasypt.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by @author JsckChin on 2022/1/29
 */
@ConfigurationProperties(prefix = "su.jasypt")
public class JasyptProperties {

    /** 是否开启 */
    boolean enabled = true;

    /** 加密盐值,默认值:egsnhm 可自定义 */
    private String password = "egsnhm";

    /** 设置加密算法的值,默认算法:PBEWithMD5AndDES */
    private String algorithm = "PBEWithMD5AndDES";

    /** 设置用于获取加密密钥的散列迭代次数,默认值:1000 */
    private Integer keyObtentionIterations = 1000;

    /** 设置要创建的加密器池的大小,默认值:1 */
    private String poolSize = "1";

    /** 设置盐生成器,默认值:org.jasypt.salt.RandomSaltGenerator */
    private String saltGeneratorClassName = "org.jasypt.salt.RandomSaltGenerator";

    /** 设置字符串输出将被编码的形式,默认值:base64 */
    private String stringOutputType = "base64";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public Integer getKeyObtentionIterations() {
        return keyObtentionIterations;
    }

    public void setKeyObtentionIterations(Integer keyObtentionIterations) {
        this.keyObtentionIterations = keyObtentionIterations;
    }

    public String getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(String poolSize) {
        this.poolSize = poolSize;
    }

    public String getSaltGeneratorClassName() {
        return saltGeneratorClassName;
    }

    public void setSaltGeneratorClassName(String saltGeneratorClassName) {
        this.saltGeneratorClassName = saltGeneratorClassName;
    }

    public String getStringOutputType() {
        return stringOutputType;
    }

    public void setStringOutputType(String stringOutputType) {
        this.stringOutputType = stringOutputType;
    }
}
