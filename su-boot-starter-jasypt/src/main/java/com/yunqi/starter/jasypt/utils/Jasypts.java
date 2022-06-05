package com.yunqi.starter.jasypt.utils;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

/**
 * Jasypt安全框架加密类工具包
 * Created by @author CHQ on 2022/1/29
 */
public class Jasypts {

    /** 默认加密盐值 */
    public static final String DEFAULT_JASYPT_SALT = "egsnhm";

    /**
     * Jasypt配置
     *
     * @param salt 加密盐值
     * @return     Jasypt配置
     */
    public static SimpleStringPBEConfig cryptOr(String salt) {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(salt);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        return config;
    }

    /**
     * 加密
     *
     * @param salt      加密盐值,配置文件中设定的加密密码 jasypt.encryptor.password
     * @param value     待加密值
     * @return          数字签名
     */
    public static String encrypt(String salt, String value) {
        PooledPBEStringEncryptor encryptOr = new PooledPBEStringEncryptor();
        encryptOr.setConfig(cryptOr(salt));
        return encryptOr.encrypt(value);
    }

    /**
     * 加密
     *
     * @param value     待加密值
     * @return          数字签名
     */
    public static String encrypt(String value) {
        return encrypt(DEFAULT_JASYPT_SALT, value);
    }


    /**
     * 解密
     *
     * @param salt     加密盐值,配置文件中设定的加密密码 jasypt.encryptor.password
     * @param value    待解密密文
     * @return         解密原文
     */
    public static String decrypt(String salt, String value) {
        PooledPBEStringEncryptor encryptOr = new PooledPBEStringEncryptor();
        encryptOr.setConfig(cryptOr(salt));
        return encryptOr.decrypt(value);
    }


    /**
     * 解密
     *
     * @param value     待解密密文
     * @return          解密原文
     */
    public static String decrypt(String value) {
        return  decrypt(DEFAULT_JASYPT_SALT, value);
    }


}
