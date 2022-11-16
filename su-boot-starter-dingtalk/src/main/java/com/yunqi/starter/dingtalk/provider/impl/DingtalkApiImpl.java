package com.yunqi.starter.dingtalk.provider.impl;

import com.yunqi.starter.common.lang.util.NutMap;
import com.yunqi.starter.dingtalk.provider.IDingtalkApi;
import com.yunqi.starter.dingtalk.util.DingtalkUtil;

/**
 * Created by @author CHQ on 2022/4/1
 */
public class DingtalkApiImpl implements IDingtalkApi {

    @Override
    public NutMap getTicket() {
        return DingtalkUtil.get("/gettoken" + DingtalkUtil.buildAppKey());
    }

}
