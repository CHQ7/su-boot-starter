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


    // ---------- 模板消息 ------------------------

    /**
     * 发送订阅通知
     *
     * @param touser      接收者（用户）的 openid
     * @param mp_template_msg 所需下发的订阅模板id
     * @param miniprogram 跳转小程序时填写，格式如{ "appid": "pagepath": { "value": any } }
     * @param data        模板内容，格式形如 { "key1": { "value": any }, "key2": { "value": any } }
     * @return
     */
    NutMap template_send(String touser, NutMap mp_template_msg,  NutMap miniprogram, NutMap data);

}
