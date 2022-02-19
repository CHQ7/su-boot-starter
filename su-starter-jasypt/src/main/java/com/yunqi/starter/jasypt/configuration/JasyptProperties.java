package com.yunqi.starter.jasypt.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by @author JsckChin on 2022/1/29
 */
@Data
@ConfigurationProperties(prefix = "su.jasypt")
public class JasyptProperties {

    boolean enabled = true;

    /**
     * 加密盐值,默认值:egsnhm 可自定义
     */
    String password = "egsnhm";

    /**
     * 设置加密算法的值,默认算法:PBEWithMD5AndDES
     */
    String algorithm = "PBEWithMD5AndDES";

    /**
     * 设置用于获取加密密钥的散列迭代次数,默认值:1000
     */
    Integer keyObtentionIterations = 1000;

    /**
     * 设置要创建的加密器池的大小,默认值:1
     */
    String poolSize = "1";

    /**
     * 设置盐生成器,默认值:org.jasypt.salt.RandomSaltGenerator
     */
    String saltGeneratorClassName = "org.jasypt.salt.RandomSaltGenerator";

    /**
     * 设置字符串输出将被编码的形式,默认值:base64
     */
    String stringOutputType = "base64";

}
