package com.yunqi.starter.wx;


import com.yunqi.starter.wx.spi.Wxs;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信小程序 accessToken
 * 单例设计模式 缓存
 * Created by @author CHQ on 2022/6/19
 */
public class WxToken {

    /**
     * 缓存accessToken 的Map,map中包含 一个accessToken 和 缓存的时间戳
     */
    private Map<String, String> map = new HashMap<String,String>();

    private WxToken() {

    }

    private static WxToken single = null;


    /**
     * 普通懒汉式
     * 静态工厂方法
     * @return  accessToken
     */
    public static WxToken getInstance() {
        if (single == null) {
            single = new WxToken();
        }
        return single;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public static WxToken getSingle() {
        return single;
    }

    public static void setSingle(WxToken single) {
        WxToken.single = single;
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
        WxToken singleton = WxToken.getInstance();
        // 获取单例map数据
        Map<String, String> map = singleton.getMap();
        //从缓存中拿数据
        String time = map.get("time");
        //从缓存中拿数据
        String accessToken = map.get("access_token");
        //从缓存中拿数据
        String expireIn = map.get("expires_in");

        // 获取系统时间戳单位为 毫秒
        long nowDate = System.currentTimeMillis();

        // 设置比过期时间少一些---> 7200s过期 设置超过7000s就刷新一次
        if (accessToken != null && time != null && nowDate - Long.parseLong(time) < 7000*1000) {
            //从缓存中拿数据为返回结果赋值
            result.put("access_token", accessToken);
            result.put("expires_in", expireIn);
        } else {
            // 请求获取新的凭证
            Map<String,Object> info = Wxs.getTicket();
            // 重新获取并覆盖原先的值
            map.put("time", nowDate + "");
            map.put("access_token", String.valueOf(info.get("access_token")));
            map.put("expires_in", String.valueOf(info.get("expires_in")));
            // 为返回结果赋值
            result.put("access_token", info.get("access_token"));
            result.put("expires_in", info.get("expires_in"));
        }
        return result;
    }
}
