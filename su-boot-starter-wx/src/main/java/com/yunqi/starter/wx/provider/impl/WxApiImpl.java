package com.yunqi.starter.wx.provider.impl;

import com.yunqi.starter.common.lang.Lang;
import com.yunqi.starter.wx.provider.WxApi;
import com.yunqi.starter.wx.spi.Wxs;
import lombok.extern.slf4j.Slf4j;
import org.nutz.http.Http;
import org.nutz.lang.util.NutMap;

/**
 * Created by @author CHQ on 2022/2/28
 */
@Slf4j
public class WxApiImpl implements WxApi {

    public WxApiImpl(){}


    @Override
    public NutMap jscode2session(String code) {
        String fmt = "%s/sns/jscode2session"
                + "?appid=%s"
                + "&secret=%s"
                + "&js_code=%s"
                + "&grant_type=authorization_code";
        String url = String.format(fmt, Wxs.domain, Wxs.appid, Wxs.appsecret, code);
        String json = Http.get(url).getContent();
        return Lang.map(json);
    }

}
