package com.yunqi.starter.dingtalk.provider.impl;

import com.yunqi.starter.common.lang.Strings;
import com.yunqi.starter.common.lang.util.NutMap;
import com.yunqi.starter.dingtalk.provider.IDingtalkBadgeCodesApi;
import com.yunqi.starter.dingtalk.util.DingtalkUtil;
import org.nutz.lang.random.R;

/**
 * Created by @author CHQ on 2022/11/16
 */
public class DingtalkBadgeCodesApiImpl implements IDingtalkBadgeCodesApi {

    @Override
    public NutMap decode(String payCode) {
        NutMap data = new NutMap();
        if(Strings.isNotBlank(payCode)){
            data.put("payCode", payCode);
        }
        data.put("requestId", R.UU32());
        return DingtalkUtil.post2("/v1.0/badge/codes/decode" , data);
    }
}
