package com.yunqi.starter.dingtalk.spi;

import com.yunqi.starter.dingtalk.configuration.DingtalkConfig;
import com.yunqi.starter.dingtalk.provider.IDingtalkProvider;
import com.yunqi.starter.dingtalk.provider.impl.DingtalkProviderImpl;
import com.yunqi.starter.dingtalk.util.DingtalkToken;
import lombok.extern.slf4j.Slf4j;
import org.nutz.json.Json;
import org.nutz.lang.util.NutMap;

/**
 * 钉钉API工具类
 * Created by @author JsckChin on 2022/4/1
 */
@Slf4j
public class Dingtalks {

    /**
     * 配置文件 Bean
     */
    public volatile static DingtalkConfig config;
    public static void setConfig(DingtalkConfig config) {
        Dingtalks.config = config;
        if(config.getIsLog()){
            log.info("打印钉钉配置如下 ->\n{}", Json.toJson(Dingtalks.config));
        }
    }

    /**
     * 底层的钉钉API对象
     */
    public static IDingtalkProvider iDingtalkProvider= new DingtalkProviderImpl();

    // =================== 获取Api 相关 ===================

    /**
     * 获取凭证
     * @return 钉钉凭证
     */
    public static String getToken(){
        return DingtalkToken.getToken();
    }

    /**
     * 获取凭证
     * @return 请求数据
     */
    public static NutMap getTicket(){
        return iDingtalkProvider.getTicket();
    }

}
