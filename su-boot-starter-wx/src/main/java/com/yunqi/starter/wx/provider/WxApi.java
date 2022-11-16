package com.yunqi.starter.wx.provider;


import com.yunqi.starter.common.lang.util.NutMap;
import com.yunqi.starter.wx.model.WxUniformMessage;

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
     * 下发统一消息
     * <pre>
     * WxUniformMessage msg = new WxUniformMessage("公众号或者小程序OPENID",
     *                 WxUniformMessage.MpTemplateMsg.builder()
     *                 .appid("公众号APPID")
     *                 .template_id("公众号模板消息ID")
     *                 .miniprogram(new WxUniformMessage.MiniProgram("小程序APPID","pages/index/index"))
     *                 .build()
     *                 .addData("first", "提示")
     *                 .addData("keyword1", "关键词1")
     *                 .addData("keyword2", "关键词1")
     *                 .addData("keyword3", "关键词1")
     *                 .addData("keyword4", "关键词1")
     *                 .addData("keyword5", "关键词1")
     *                 .addData("remark", "备注")
     *         );
     * </pre>
     *
     * @param msg   模板消息
     */
    void templateUniformSend(WxUniformMessage msg);



}
