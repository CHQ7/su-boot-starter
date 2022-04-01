package com.yunqi.starter.dingtalk.provider.impl;

import com.yunqi.starter.dingtalk.provider.IDingtalkProvider;
import com.yunqi.starter.dingtalk.spi.Dingtalks;
import com.yunqi.starter.dingtalk.util.DingtalkUtil;
import org.nutz.lang.util.NutMap;

/**
 * Created by @author JsckChin on 2022/4/1
 */
public class DingtalkProviderImpl implements IDingtalkProvider {

    @Override
    public NutMap getTicket() {
        String url = String.format("/gettoken?appkey=%s&appsecret=%s", Dingtalks.config.getAppkey(), Dingtalks.config.getAppsecret());
        return DingtalkUtil.get(url);
    }

}
