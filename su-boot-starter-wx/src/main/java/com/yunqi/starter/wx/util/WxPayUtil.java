package com.yunqi.starter.wx.util;

import com.yunqi.starter.common.lang.Lang;
import com.yunqi.starter.common.lang.Strings;
import com.yunqi.starter.common.lang.util.NutMap;
import org.nutz.lang.Files;
import org.nutz.lang.random.R;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付V3工具类
 * Created by @author CHQ on 2022/9/17
 */
public class WxPayUtil {

    private static final String OS = System.getProperty("os.name") + "/" + System.getProperty("os.version");
    private static final String VERSION = System.getProperty("java.version");

    /**
     * 生成 V3 Authorization
     *
     * @param method        GET/POST等请求方法
     * @param urlSuffix     URL
     * @param mchId         商户号
     * @param serialNo      V3API证书序列号
     * @param privateKey    V3Key证书文件路径
     * @param body          body
     * @param nonceStr      随机字符串
     * @param timestamp     时间戳
     * @param authType      授权类型
     * @return              Authorization
     */
    public static String buildAuthorization(String method, String urlSuffix, String mchId,
                                            String serialNo, PrivateKey privateKey, String body,
                                            String nonceStr, long timestamp, String authType) {
        // 构建签名参数
        String buildSignMessage = buildSignMessage(method, urlSuffix, timestamp, nonceStr, body);
        // 数字签名
        String signature =  Lang.sign(privateKey, buildSignMessage, "SHA256withRSA");
        // 根据平台规则生成请求头 authorization
        return getAuthorization(mchId, serialNo, nonceStr, String.valueOf(timestamp), signature, authType);
    }

    /**
     * 生成 V3 Authorization
     *
     * @param method        GET/POST等请求方法
     * @param url           URL
     * @param mchId         商户号
     * @param serialNo      V3API证书序列号
     * @param keyPath       V3Key证书文件路径
     * @param body          body
     * @param nonceStr      随机字符串
     * @param timestamp     时间戳
     * @param authType      授权类型
     * @return              authorization
     * @throws Exception    Exception
     */
    public static String buildAuthorization(String method, String url, String mchId,
                                            String serialNo, String keyPath, String body,
                                            String nonceStr, long timestamp, String authType) throws Exception {
        String buildSignMessage = buildSignMessage(method, url, timestamp, nonceStr, body);
        String signature = createSign(buildSignMessage, keyPath);
        return getAuthorization(mchId, serialNo, nonceStr, String.valueOf(timestamp), signature, authType);
    }

    /**
     * 生成 V3 Authorization
     *
     * @param mchId         商户号
     * @param serialNo      V3API证书序列号
     * @param nonceStr      随机字符串
     * @param timestamp     时间戳
     * @param signature     数字签名
     * @param authType      授权类型
     * @return              authorization
     */
    public static String getAuthorization(String mchId, String serialNo, String nonceStr, String timestamp, String signature, String authType) {
        Map<String, String> params = new HashMap<>(5);
        params.put("mchid", mchId);
        params.put("serial_no", serialNo);
        params.put("nonce_str", nonceStr);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        return authType.concat(" ").concat(Strings.createLinkString(params, ",", false, true));
    }

    /**
     * 组装请求头
     *
     * @param authorization authorization
     * @param serialNumber  证书序列号
     * @return              请求头
     */
    public static Map<String, String> getHeaders(String authorization, String serialNumber) {
        Map<String, String> headers = getBaseHeaders(authorization);
        headers.put("Content-Type", "application/json");
        if (Strings.isNotBlank(serialNumber)) {
            headers.put("Wechatpay-Serial", serialNumber);
        }
        return headers;
    }

    /**
     * 组装请求头
     *
     * @param authorization authorization
     * @return              请求头
     */
    public static Map<String, String> getBaseHeaders(String authorization) {
        String userAgent = String.format(
                "WeChatPay-HttpClient/%s (%s) Java/%s",
                WxPayUtil.class.getPackage().getImplementationVersion(),
                OS,
                VERSION == null ? "Unknown" : VERSION);

        Map<String, String> headers = new HashMap<>(5);
        headers.put("Accept", "application/json");
        headers.put("Authorization", authorization);
        headers.put("User-Agent", userAgent);
        return headers;
    }

    /**
     * 构造签名字符串
     *
     * @param method    GET/POST等请求方法
     * @param url       URL
     * @param timestamp 时间戳
     * @param nonceStr  随机字符串
     * @param body      body
     * @return          签名参数
     */
    public static String buildSignMessage(String method, String url, long timestamp, String nonceStr, String body) {
        return method + "\n"
                + url + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + body + "\n";
    }

    /**
     * 构造签名字符串
     *
     * @param timestamp 时间戳
     * @param nonceStr  随机字符串
     * @param body      body
     * @return          签名参数
     */
    public static String buildSignMessage(String timestamp, String nonceStr, String body) {
        return timestamp + "\n"
                + nonceStr + "\n"
                + body + "\n";
    }

    /**
     * 签名算法 通过私钥路径
     *
     * @param keyPath       私钥文件路径
     * @param data          待加密数据
     * @return              数字签名
     * @throws Exception    Exception
     */
    public static String createSign(String data, String keyPath) throws Exception {
        if (Strings.isBlank(data)) {
            return null;
        } else {
            PrivateKey privateKey = getPrivateKey(keyPath);
            return Lang.sign(privateKey, data, "SHA256withRSA");
        }
    }

    /**
     * 通过文件路径获取私钥
     *
     * @param keyPath       私钥文件路径
     * @return              私钥
     * @throws Exception    Exception
     */
    public static PrivateKey getPrivateKey(String keyPath) throws Exception {
        String originalKey = new String(Files.readBytes(keyPath), StandardCharsets.UTF_8);
        return Lang.privateKey(originalKey);
    }

    /**
     * 获取jsapi签名内容
     *
     * @param appId         APPID
     * @param prepay_id     prepay_id(微信预付款ID)
     * @param keyPath       私钥文件路径
     * @return              jsapi参数
     * @throws Exception    Exception
     */
    public static NutMap getJsapiSignMessage(String appId, String prepay_id, String keyPath) throws Exception {
        String packageStr = "prepay_id=" + prepay_id;
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr = R.UU32().toUpperCase();
        String signMessage = appId + "\n"
                + timeStamp + "\n"
                + nonceStr + "\n"
                + packageStr + "\n";
        String sign = createSign(signMessage, keyPath);
        NutMap params = NutMap.NEW();
        params.put("appId", appId);
        params.put("timeStamp", timeStamp);
        params.put("nonceStr", nonceStr);
        params.put("package", "prepay_id=" + prepay_id);
        params.put("signType", "RSA");
        params.put("paySign", sign);
        return params;
    }

}
