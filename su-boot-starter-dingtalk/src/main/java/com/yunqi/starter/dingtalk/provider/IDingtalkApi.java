package com.yunqi.starter.dingtalk.provider;

import com.yunqi.starter.common.lang.util.NutMap;

/**
 * 钉钉接口
 * Created by @author CHQ on 2022/4/1
 */
public interface IDingtalkApi {

    /**
     * 获取凭证
     * @return 请求数据
     */
    NutMap getTicket();


}
