package com.yunqi.starter.dingtalk.provider.impl;

import com.yunqi.starter.common.lang.Lang;
import com.yunqi.starter.common.lang.Strings;
import com.yunqi.starter.common.lang.util.NutMap;
import com.yunqi.starter.common.repo.Base64;
import com.yunqi.starter.dingtalk.provider.IDingtalkOauthApi;
import com.yunqi.starter.dingtalk.spi.Dingtalks;
import com.yunqi.starter.dingtalk.util.DingtalkUtil;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by @author CHQ on 2022/11/16
 */
public class DingtalkOauthApiImpl implements IDingtalkOauthApi {

    @Override
    public NutMap auth(String code) {
        NutMap data = new NutMap();
        if(Strings.isNotBlank(code)){
            data.put("code", code);
        }
        NutMap res = DingtalkUtil.post("/topapi/v2/user/getuserinfo" + DingtalkUtil.buildToken(), data);
        return Lang.map(res.getString("result"));
    }

    @Override
    public NutMap authsns(String code) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        NutMap data = new NutMap();
        if(Strings.isNotBlank(code)){
            data.put("tmp_auth_code", code);
        }
        String timestamp = String.valueOf(System.currentTimeMillis());
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(Dingtalks.config.getAppsecret().getBytes("UTF-8"), "HmacSHA256"));
        byte[] signatureBytes = mac.doFinal(timestamp.getBytes("UTF-8"));
        String signature = Base64.encode(signatureBytes);
        String urlEncodeSignature = URLEncoder.encode(signature, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("~", "%7E").replace("/", "%2F");
        NutMap res = DingtalkUtil.post("/sns/getuserinfo_bycode" + String.format("?accessKey=%s&timestamp=%s&signature=%s", Dingtalks.config.getAppkey(), timestamp, urlEncodeSignature), data);
        return Lang.map(res.getString("user_info"));
    }

}
