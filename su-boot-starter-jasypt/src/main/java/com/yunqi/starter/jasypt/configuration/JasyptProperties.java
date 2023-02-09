package com.yunqi.starter.jasypt.configuration;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by @author CHQ on 2022/1/29
 */
@Getter
@Setter
@ConfigurationProperties(prefix = JasyptProperties.PREFIX)
public class JasyptProperties {

    public static final String PREFIX = "su.jasypt";

    /**  Jasypt 加密器开关，默认开启 */
    boolean enabled = true;

    /** Jasypt 加密密钥 */
    private String password = "jasypt";

    /** Jasypt 加密算法 */
    private String algorithm = "PBEWITHHMACSHA512ANDAES_256";

    /** Jasypt 密钥获取次数 */
    private Integer keyObtentionIterations = 1000;

    /** Jasypt 密钥池大小 */
    private String poolSize = "1";

    /** Jasypt 加密提供者名称 */
    private String providerName = "SunJCE";

    /** Jasypt Salt生成器类名 */
    private String saltGeneratorClassName = "org.jasypt.salt.RandomSaltGenerator";

    /**  Jasypt IV生成器类名 */
    private String ivGeneratorClassName = "org.jasypt.iv.RandomIvGenerator";

    /** Jasypt 加密字符串输出类型 */
    private String stringOutputType = "base64";
}
