package com.yunqi.starter.wxpay.util;

import com.yunqi.starter.common.json.Json;
import com.yunqi.starter.common.lang.Lang;
import com.yunqi.starter.common.lang.Strings;
import com.yunqi.starter.common.lang.util.NutMap;
import com.yunqi.starter.common.repo.Base64;
import com.yunqi.starter.wxpay.model.WxPayResponse;
import com.yunqi.starter.wxpay.spi.WxPays;
import org.nutz.http.Request;
import org.nutz.http.Response;
import org.nutz.http.Sender;
import org.nutz.lang.Files;
import org.nutz.lang.random.R;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付V3工具类
 * Created by @author CHQ on 2022/9/17
 */
public class WxPayUtil {

    private static final int TAG_LENGTH_BIT = 128;
    private static final String OS = System.getProperty("os.name") + "/" + System.getProperty("os.version");
    private static final String VERSION = System.getProperty("java.version");


    /**
     * 发起http请求
     *
     * @param method        请求方法
     * @param url           URL后缀
     * @param platSerialNo  平台证书序列号
     * @param body          传参
     * @return              WxPayResponse
     * @throws Exception    Exception
     */
    public static WxPayResponse call(String method, String url,
                                     String platSerialNo,
                                     String body) throws Exception {

        // 时间戳
        long timestamp = System.currentTimeMillis() / 1000;

        // 随机字符串
        String nonceStr = R.UU32().toUpperCase();

        // 获取V3API序列号
        String serialNo = Lang.getCertSerialNo(WxPays.config.getCertPemPath());

        // 认证类型
        String authType = "WECHATPAY2-SHA256-RSA2048";

        // 生成 V3 Authorization
        String authorization = buildAuthorization(method, url,
                WxPays.config.getMchId(),
                serialNo,
                WxPays.config.getKeyPemPath(),
                body, nonceStr, timestamp, authType);

        if (Strings.isBlank(platSerialNo)) {
            platSerialNo = serialNo;
        }
        if ("GET".equals(method)) {
            return call_get(WxPays.config.getDomain() + url, authorization, platSerialNo);
        }
        if ("POST".equals(method)) {
            return call_post(WxPays.config.getDomain() + url, authorization, platSerialNo, body);
        }
        return null;
    }

    /**
     * get请求
     *
     * @param url               URL后缀
     * @param authorization     authorization
     * @param serialNumber      平台证书序列号
     * @return                  WxPayResponse
     */
    public static WxPayResponse call_get(String url, String authorization, String serialNumber) {
        Request req = Request.create(url, Request.METHOD.GET);
        req.getHeader().addAll(getHeaders(authorization, serialNumber));
        Sender sender = Sender.create(req);
        Response resp = sender.send();
        if (!resp.isOK())
            throw new IllegalStateException("resp code=" + resp.getStatus() + ",msg=" + resp.getContent());
        WxPayResponse wxPayResponse = new WxPayResponse();
        wxPayResponse.setBody(resp.getContent("UTF-8"));
        wxPayResponse.setHeader(resp.getHeader());
        wxPayResponse.setStatus(resp.getStatus());
        return wxPayResponse;
    }

    /**
     * post请求
     *
     * @param url               URL后缀
     * @param authorization     authorization
     * @param serialNumber      平台证书序列号
     * @param body              body参数
     * @return                  WxPayResponse
     */
    public static WxPayResponse call_post(String url, String authorization, String serialNumber, String body) {
        Request req = Request.create(url, Request.METHOD.POST);
        req.setData(body);
        req.getHeader().addAll(getHeaders(authorization, serialNumber));
        Sender sender = Sender.create(req);
        Response resp = sender.send();
        if (!resp.isOK())
            throw new IllegalStateException("resp code=" + resp.getStatus() + ",msg=" + resp.getContent());
        WxPayResponse wxPayResponse = new WxPayResponse();
        wxPayResponse.setBody(resp.getContent("UTF-8"));
        wxPayResponse.setHeader(resp.getHeader());
        wxPayResponse.setStatus(resp.getStatus());
        return wxPayResponse;
    }

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
     * @param prepay_id     prepay_id(微信预付款ID)
     * @return              jsapi参数
     * @throws Exception    Exception
     */
    public static NutMap getJsapiSignMessage(String prepay_id) throws Exception {
        String appId = WxPays.config.getAppId();
        String keyPath = WxPays.config.getKeyPemPath();
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

    /**
     * 验证微信服务返回的数据签名
     *
     * @param wxPayResponse     微信服务器返回的对象
     * @return                  boolean
     */
    public static boolean verifySignature(WxPayResponse wxPayResponse) {
        InputStream inputStream = new ByteArrayInputStream(getPlatfromCert().getBytes());
        // 获取平台证书序列号
        X509Certificate certificate = Lang.getCertificate(inputStream);
        String body = wxPayResponse.getBody();
        String signature = wxPayResponse.getHeader().get("Wechatpay-Signature");
        String nonce = wxPayResponse.getHeader().get("Wechatpay-Nonce");
        String timestamp = wxPayResponse.getHeader().get("Wechatpay-Timestamp");
        return verifySignature(signature, body, nonce, timestamp, certificate.getPublicKey());
    }

    /**
     * 获取微信平台证书
     * @return  微信平台证书
     */
    private static String getPlatfromCert(){
        return new String(Files.readBytes(WxPays.config.getPlatformCertPath()), StandardCharsets.UTF_8);
    }


    /**
     * v3 支付异步通知验证签名
     *
     * @param serialNo     证书序列号
     * @param body         异步通知密文
     * @param signature    签名
     * @param nonce        随机字符串
     * @param timestamp    时间戳
     * @return 异步通知明文
     * @throws Exception 异常信息
     */
    public static String verifyNotify(String serialNo, String body,
                                      String signature, String nonce,
                                      String timestamp) throws Exception {
        InputStream inputStream = new ByteArrayInputStream(getPlatfromCert().getBytes());
        // 获取平台证书
        X509Certificate certificate = Lang.getCertificate(inputStream);
        // 获取平台证书序列号
        String serialNumber = certificate.getSerialNumber().toString(16).toUpperCase();
        // V3API密钥
        String key = WxPays.config.getApiKey3();
        // 验证证书序列号
        if (serialNumber.equals(serialNo)) {
            boolean verifySignature = verifySignature(signature, body, nonce, timestamp, certificate.getPublicKey());
            if (verifySignature) {
                NutMap resMap = Json.fromJson(NutMap.class, body);
                NutMap resource = resMap.getAs("resource", NutMap.class);
                String cipherText = resource.getString("ciphertext");
                String nonceStr = resource.getString("nonce");
                String associatedData = resource.getString("associated_data");
                // 密文解密
                return decryptToString(
                        key.getBytes(StandardCharsets.UTF_8),
                        associatedData.getBytes(StandardCharsets.UTF_8),
                        nonceStr.getBytes(StandardCharsets.UTF_8),
                        cipherText
                );
            }
        }
        return null;
    }


    /**
     * 证书和回调报文解密
     *
     * @param associatedData associated_data
     * @param nonce          nonce
     * @param cipherText     ciphertext
     * @return
     * @throws GeneralSecurityException 异常
     */
    public static String decryptToString(byte[] aesKey, byte[] associatedData, byte[] nonce, String cipherText) throws GeneralSecurityException {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            SecretKeySpec key = new SecretKeySpec(aesKey, "AES");
            GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH_BIT, nonce);

            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            cipher.updateAAD(associatedData);

            return new String(cipher.doFinal(Base64.decode(cipherText)), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new IllegalStateException(e);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 验证签名
     *
     * @param signature 待验证的签名
     * @param body      应答主体
     * @param nonce     随机串
     * @param timestamp 时间戳
     * @param publicKey 微信支付平台公钥
     * @return 签名结果
     */
    public static boolean verifySignature(String signature, String body, String nonce, String timestamp, PublicKey publicKey) {
        String buildSignMessage = buildSignMessage(timestamp, nonce, body);
        return Lang.verifySign(publicKey, signature, buildSignMessage,"SHA256withRSA");
    }

}
