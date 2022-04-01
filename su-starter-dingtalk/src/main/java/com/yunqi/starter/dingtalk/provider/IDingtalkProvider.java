package com.yunqi.starter.dingtalk.provider;

import org.nutz.lang.util.NutMap;

/**
 * 钉钉接口
 * Created by @author JsckChin on 2022/4/1
 */
public interface IDingtalkProvider {

    /**
     * 获取凭证
     * @return 请求数据
     */
    NutMap getTicket();

}
