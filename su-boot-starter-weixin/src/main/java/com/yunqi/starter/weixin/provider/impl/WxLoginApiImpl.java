package com.yunqi.starter.weixin.provider.impl;

import com.yunqi.starter.common.json.Json;
import com.yunqi.starter.common.lang.Strings;
import com.yunqi.starter.common.lang.util.NutMap;
import com.yunqi.starter.weixin.provider.WxLoginApi;
import com.yunqi.starter.weixin.provider.WxResp;
import com.yunqi.starter.weixin.spi.WeiXins;
import org.nutz.http.Request;
import org.nutz.http.Response;
import org.nutz.http.Sender;

/**
 * Created by @author CHQ on 2022/7/27
 */
public class WxLoginApiImpl implements WxLoginApi {


    @Override
    public String qrconnect(String redirect_uri, String scope, String state) {
        Request req = Request.create("https://open.weixin.qq.com/connect/qrconnect", Request.METHOD.GET);
        NutMap params = new NutMap();
        params.put("appid", WeiXins.config.getAppkey());
        params.put("redirect_uri", redirect_uri);
        params.put("response_type", "code");
        params.put("scope", Strings.sBlank(scope, "snsapi_login"));
        req.setParams(params);
        return req.getUrl().toString() + "#wechat_redirect";
    }

    @Override
    public String authorize(String redirect_uri, String scope, String state) {
        Request req = Request.create("https://open.weixin.qq.com/connect/oauth2/authorize", Request.METHOD.GET);
        NutMap params = new NutMap();
        params.put("appid", WeiXins.config.getAppkey());
        params.put("redirect_uri", redirect_uri);
        params.put("response_type", "code");
        params.put("scope", Strings.sBlank(scope, "snsapi_userinfo"));
        req.setParams(params);
        return req.getUrl().toString() + "#wechat_redirect";
    }

    @Override
    public WxResp access_token(String code) {
        Request req = Request.create(WeiXins.config.getDomain() + "/sns/oauth2/access_token", Request.METHOD.GET);
        NutMap params = new NutMap();
        params.put("appid", WeiXins.config.getAppkey());
        params.put("secret", WeiXins.config.getAppsecret());
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        req.setParams(params);
        Response resp = Sender.create(req).send();
        if (!resp.isOK()) {
            return null;
        }
        return Json.fromJson(WxResp.class, resp.getReader("UTF-8"));
    }

    @Override
    public WxResp refresh_token(String refresh_token) {
        Request req = Request.create(WeiXins.config.getDomain() + "/sns/oauth2/refresh_token", Request.METHOD.GET);
        NutMap params = new NutMap();
        params.put("appid", WeiXins.config.getAppkey());
        params.put("secret", WeiXins.config.getAppsecret());
        params.put("refresh_token", refresh_token);
        params.put("grant_type", "refresh_token");
        req.setParams(params);
        Response resp = Sender.create(req).send();
        if (!resp.isOK()) {
            return null;
        }
        return Json.fromJson(WxResp.class, resp.getReader("UTF-8"));
    }

    @Override
    public WxResp auth(String openid, String access_token) {
        Request req = Request.create(WeiXins.config.getDomain() + "/sns/auth", Request.METHOD.GET);
        NutMap params = new NutMap();
        params.put("access_token", access_token);
        params.put("openid", openid);
        req.setParams(params);
        Response resp = Sender.create(req).send();
        if (!resp.isOK()) {
            return null;
        }
        return Json.fromJson(WxResp.class, resp.getReader("UTF-8"));
    }

    @Override
    public WxResp userinfo(String openid, String access_token) {
        Request req = Request.create(WeiXins.config.getDomain() + "/sns/userinfo", Request.METHOD.GET);
        NutMap params = new NutMap();
        params.put("access_token", access_token);
        params.put("openid", openid);
        req.setParams(params);
        Response resp = Sender.create(req).send();
        if (!resp.isOK()) {
            return null;
        }
        return Json.fromJson(WxResp.class, resp.getReader("UTF-8"));
    }
}
