package com.yunqi.starter.dingtalk.spi;

import com.yunqi.starter.common.json.Json;
import com.yunqi.starter.common.lang.util.NutMap;
import com.yunqi.starter.dingtalk.configuration.DingtalkConfig;
import com.yunqi.starter.dingtalk.provider.*;
import com.yunqi.starter.dingtalk.provider.impl.*;
import com.yunqi.starter.dingtalk.DingtalkToken;
import lombok.extern.slf4j.Slf4j;

/**
 * 钉钉API工具类
 * Created by @author CHQ on 2022/4/1
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
    public static IDingtalkApi api = new DingtalkApiImpl();

    /**
     * 部门API
     */
    public static IDingtalkDepartmentApi department = new DingtalkDepartmentApiImpl();

    /**
     * 角色API
     */
    public static IDingtalkRoleApi role = new DingtalkRoleApiImpl();

    /**
     * 用户API
     */
    public static IDingtalkUserApi user = new DingtalkUserApiImpl();

    /**
     * 身份授权API
     */
    public static IDingtalkOauthApi oauth = new DingtalkOauthApiImpl();

    /**
     * 钉工牌API
     */
    public static IDingtalkBadgeCodesApi badgeCodes = new DingtalkBadgeCodesApiImpl();



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
        return api.getTicket();
    }


}
