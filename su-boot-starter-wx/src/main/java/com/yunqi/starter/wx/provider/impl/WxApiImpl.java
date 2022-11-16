package com.yunqi.starter.wx.provider.impl;

import com.yunqi.starter.common.json.Json;
import com.yunqi.starter.common.lang.Lang;
import com.yunqi.starter.common.lang.Strings;
import com.yunqi.starter.common.lang.util.NutMap;
import com.yunqi.starter.wx.provider.WxApi;
import com.yunqi.starter.wx.spi.Wxs;
import com.yunqi.starter.wx.util.WxUtil;
import lombok.extern.slf4j.Slf4j;
import org.nutz.http.Http;

/**
 * Created by @author CHQ on 2022/2/28
 */
@Slf4j
public class WxApiImpl implements WxApi {

    public WxApiImpl(){}


    @Override
    public NutMap getTicket() {
        String fmt = "%s/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
        String url = String.format(fmt, Wxs.config.getDomain(), Wxs.config.getAppkey(), Wxs.config.getAppsecret());
        String json = Http.get(url).getContent();
        return Json.fromJson(NutMap.class, json);
    }

    @Override
    public NutMap jscode2session(String code) {
        String fmt = "%s/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
        String url = String.format(fmt, Wxs.config.getDomain(), Wxs.config.getAppkey(), Wxs.config.getAppsecret(), code);
        String json = Http.get(url).getContent();
        return Lang.map(json);
    }

    @Override
    public String getPhoneNumber(String code) {
        NutMap data = new NutMap();
        data.put("code", code);
        NutMap res = WxUtil.post("/wxa/business/getuserphonenumber" + WxUtil.buildToken(), data);
        return Lang.map(res.getString("phone_info")).getString("purePhoneNumber");
    }

    @Override
    public NutMap template_send(String touser, NutMap mp_template_msg,  NutMap miniprogram, NutMap data) {
        NutMap body = NutMap.NEW();
        body.addv("touser", touser);
        if (Lang.isNotEmpty(mp_template_msg)) {
            body.addv("mp_template_msg", mp_template_msg);
        }
        body.addv("data", data);
        return postJson("/message/subscribe/bizsend", body);
    }

}
