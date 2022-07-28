package com.yunqi.starter.weixin.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import com.yunqi.starter.weixin.spi.WeiXins;

/**
 * 注入所需要的Bean
 * Created by @author CHQ on 2022/7/27
 */
public class WeiXinBeanInject {

    /**
     * 注入配置Bean
     *
     * @param config 配置对象
     */
    @Autowired(required = false)
    public void setConfig(WeiXinConfig config) {
        WeiXins.setConfig(config);
    }
}
