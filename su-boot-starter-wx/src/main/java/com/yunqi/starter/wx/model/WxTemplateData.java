package com.yunqi.starter.wx.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 参考 <a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/mp-message-management/uniform-message/sendUniformMessage.html">接口说明</a>
 * Created by @author CHQ on 2022/11/13
 */
@Data
@NoArgsConstructor
public class WxTemplateData {

    private String name;
    private String value;
    private String color;

    public WxTemplateData(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public WxTemplateData(String name, String value, String color) {
        this.name = name;
        this.value = value;
        this.color = color;
    }
}
