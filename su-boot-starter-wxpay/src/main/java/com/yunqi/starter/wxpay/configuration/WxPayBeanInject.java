package com.yunqi.starter.wxpay.configuration;

import com.yunqi.starter.wxpay.spi.WxPays;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 注入所需要的Bean
 * Created by @author CHQ on 2022/6/19
 */
public class WxPayBeanInject {

    /**
     * 注入配置Bean
     *
     * @param config 配置对象
     */
    @Autowired(required = false)
    public void setConfig(WxPayConfig config) {
        WxPays.setConfig(config);
    }
}
