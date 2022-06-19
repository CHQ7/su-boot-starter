package com.yunqi.starter.wx.provider;


import com.yunqi.starter.common.lang.util.NutMap;

/**
 * Created by @author CHQ on 2022/2/28
 */
public interface WxApi {

    /**
     * 获取凭证
     * @return 请求数据
     */
    NutMap getTicket();


    /**
     * 登录凭证校验
     * @param code 登录时获取的 code
     * @return     请求数据
     */
    NutMap jscode2session(String code);


    /**
     * code换取用户手机号
     * @param code  手机号获取凭证
     * @return      手机号
     */
    String getPhoneNumber(String code);
}
