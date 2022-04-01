package com.yunqi.starter.dingtalk.util;

import com.yunqi.starter.dingtalk.spi.Dingtalks;

import java.util.HashMap;
import java.util.Map;

/**
 * 钉钉 accessToken
 * 单例设计模式 缓存
 * Created by @author JsckChin on 2022/4/1
 */
public class DingtalkToken {

    /**
     * 缓存accessToken 的Map,map中包含 一个accessToken 和 缓存的时间戳
     */
    private Map<String, String> map = new HashMap<String,String>();

    private DingtalkToken() {

    }

    private static DingtalkToken single = null;


    /**
     * 普通懒汉式
     * 静态工厂方法
     * @return
     */
    public static DingtalkToken getInstance() {
        if (single == null) {
            single = new DingtalkToken();
        }
        return single;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public static DingtalkToken getSingle() {
        return single;
    }

    public static void setSingle(DingtalkToken single) {
        DingtalkToken.single = single;
    }


    public static String getToken(){
        return (String) getInstance().getAccessToken().get("access_token");
    }

    /**
     * 获取 accessToken Jsapi_ticket 已加入缓存机制
     * @return 集合数据
     */
    public Map<String,Object> getAccessToken() {
        Map<String,Object> result = new HashMap<>();
        // 获取单例
        DingtalkToken singleton = DingtalkToken.getInstance();
        // 获取单例map数据
        Map<String, String> map = singleton.getMap();
        //从缓存中拿数据
        String time = map.get("time");
        //从缓存中拿数据
        String accessToken = map.get("access_token");
        //从缓存中拿数据
        String expireIn = map.get("expireIn");

        // 获取系统时间戳单位为 毫秒
        long nowDate = System.currentTimeMillis();

        // 设置比过期时间少一些---> 7200s过期 设置超过7000s就刷新一次
        if (accessToken != null && time != null && nowDate - Long.parseLong(time) < 7000*1000) {
            //从缓存中拿数据为返回结果赋值
            result.put("access_token", accessToken);
            result.put("expireIn", expireIn);
        } else {
            // 请求获取新的凭证
            Map<String,Object> info = Dingtalks.getTicket();
            // 重新获取并覆盖原先的值
            map.put("time", nowDate + "");
            map.put("access_token", String.valueOf(info.get("access_token")));
            map.put("expireIn", String.valueOf(info.get("expires_in")));
            // 为返回结果赋值
            result.put("access_token", info.get("access_token"));
            result.put("expireIn", info.get("expires_in"));
        }
        return result;
    }
}
