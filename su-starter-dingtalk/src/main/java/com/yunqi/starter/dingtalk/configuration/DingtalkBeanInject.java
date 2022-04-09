package com.yunqi.starter.dingtalk.configuration;

import com.yunqi.starter.dingtalk.spi.Dingtalks;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 注入所需要的Bean
 * Created by @author CHQ on 2022/4/1
 */
public class DingtalkBeanInject {

    /**
     * 注入配置Bean
     *
     * @param config 配置对象
     */
    @Autowired(required = false)
    public void setConfig(DingtalkConfig config) {
        Dingtalks.setConfig(config);
    }
}
