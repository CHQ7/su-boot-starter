package com.yunqi.starter.wx.configuration;

import com.yunqi.starter.wx.spi.Wxs;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 注入所需要的Bean
 * Created by @author CHQ on 2022/6/19
 */
public class WxBeanInject {

    /**
     * 注入配置Bean
     *
     * @param config 配置对象
     */
    @Autowired(required = false)
    public void setConfig(WxConfig config) {
        Wxs.setConfig(config);
    }
}
