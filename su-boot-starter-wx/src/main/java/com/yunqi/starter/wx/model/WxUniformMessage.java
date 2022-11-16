package com.yunqi.starter.wx.model;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 模板消息
 * <p>
 * 参考 <a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/mp-message-management/uniform-message/sendUniformMessage.html">接口说明</a>
 * Created by @author CHQ on 2022/11/13
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WxUniformMessage implements Serializable {

    private static final long serialVersionUID = 6491123872729710708L;


    /**
     * 用户openid.
     * 可以是小程序的openid，也可以是mp_template_msg.appid对应的公众号的openid
     */
    private String touser;

    /**
     * 公众号模板消息相关的信息
     * 可以参考公众号模板消息接口；有此节点并且没有weapp_template_msg节点时，发送公众号模板消息
     */
    private MpTemplateMsg mp_template_msg;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MpTemplateMsg implements Serializable {

        private static final long serialVersionUID = 4075505398002485684L;

        /**
         * 公众号appid，要求与小程序有绑定且同主体.
         */
        private String appid;

        /**
         * 公众号模板ID.
         */
        private String template_id;

        /**
         * 公众号模板消息所要跳转的url.
         */
        private String url;

        /**
         * 是否发送公众号模版消息，否则发送小程序模版消息.
         */
        private boolean isMpTemplateMsg;

        /**
         * 公众号模板消息所要跳转的小程序，小程序的必须与公众号具有绑定关系.
         */
        private MiniProgram miniprogram;

        /**
         * 小程序模板数据.
         */
        private List<WxTemplateData> data;

        public MpTemplateMsg addData(WxTemplateData datum) {
            if (this.data == null) {
                this.data = new ArrayList<>();
            }
            this.data.add(datum);
            return this;
        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MiniProgram implements Serializable {

        private static final long serialVersionUID = -1277855564317016605L;

        private String appid;
        private String pagepath;
    }
}
