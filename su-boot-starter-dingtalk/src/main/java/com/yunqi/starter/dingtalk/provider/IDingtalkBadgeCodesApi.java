package com.yunqi.starter.dingtalk.provider;

import com.yunqi.starter.common.lang.util.NutMap;

/**
 * 钉钉钉工牌接口
 * Created by @author CHQ on 2022/11/16
 */
public interface IDingtalkBadgeCodesApi {

    /**
     * 解码钉工牌电子码
     * @param payCode   码值，解码接口仅支持钉钉侧生成的码值
     * @return          解码信息
     */
    NutMap decode(String payCode);
}
