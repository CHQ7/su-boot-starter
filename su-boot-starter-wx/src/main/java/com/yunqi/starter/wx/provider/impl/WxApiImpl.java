package com.yunqi.starter.wx.provider.impl;

import com.yunqi.starter.common.json.Json;
import com.yunqi.starter.common.lang.Lang;
import com.yunqi.starter.common.lang.util.NutMap;
import com.yunqi.starter.wx.model.WxUniformMessage;
import com.yunqi.starter.wx.provider.WxApi;
import com.yunqi.starter.wx.util.WxUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by @author CHQ on 2022/2/28
 */
@Slf4j
public class WxApiImpl implements WxApi {

    public WxApiImpl(){}


    @Override
    public NutMap getTicket() {
        String fmt = "/cgi-bin/token?%s&grant_type=client_credential";
        String url = String.format(fmt, WxUtil.buildAppKey());
        return WxUtil.get(url);
    }

    @Override
    public NutMap jscode2session(String code) {
        String fmt = "/sns/jscode2session?%s&js_code=%s&grant_type=authorization_code";
        String url = String.format(fmt, WxUtil.buildAppKey(), code);
        return WxUtil.get(url);
    }

    @Override
    public String getPhoneNumber(String code) {
        NutMap data = new NutMap();
        data.put("code", code);
        NutMap res = WxUtil.post("/wxa/business/getuserphonenumber" + WxUtil.buildToken(), data);
        return Lang.map(res.getString("phone_info")).getString("purePhoneNumber");
    }

    @Override
    public void templateUniformSend(WxUniformMessage msg) {
        WxUtil.post("/cgi-bin/message/wxopen/template/uniform_send" + WxUtil.buildToken(),  Json.toJson(msg));
    }



}
