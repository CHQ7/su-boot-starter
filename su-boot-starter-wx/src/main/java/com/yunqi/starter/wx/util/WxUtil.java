package com.yunqi.starter.wx.util;

import com.yunqi.starter.common.json.Json;
import com.yunqi.starter.common.lang.Lang;
import com.yunqi.starter.common.lang.Strings;
import com.yunqi.starter.common.lang.util.NutMap;
import com.yunqi.starter.wx.spi.Wxs;
import lombok.extern.slf4j.Slf4j;
import org.nutz.http.Request;
import org.nutz.http.Response;
import org.nutz.http.Sender;

/**
 * 微信小程序内部工具类
 * Created by @author CHQ on 2022/6/19
 */
@Slf4j
public class WxUtil {


    /**
     * GET请求
     * @param url 请求地址
     * @return NutMap
     */
    public static NutMap get(String url){
        // 发起网络请求
        Request req = Request.create(Wxs.config.getDomain() + url, Request.METHOD.GET);
        if(Wxs.config.getIsLog()){
            log.info("打印[请求URL] ->\n{}", req.getUrl());
        }
        // 获取请求数据
        Response resp = Sender.create(req).send();
        NutMap res = Lang.map(resp.getContent());
        if(Wxs.config.getIsLog()){
            log.info("打印[请求结果] ->\n{}", Json.toJson(res));
        }
        return res;
    }

    /**
     * POST请求
     * @param url    请求地址
     * @param data   请求数据
     * @return       map
     */
    public static NutMap post(String url, NutMap data){
        return post(url, Json.toJson(data));
    }

    public static NutMap post(String url, String data){
        // 发起网络请求
        Request req = Request.create(Wxs.config.getDomain() + url, Request.METHOD.POST);

        // 设置Json请求格式
        req.getHeader().set("Content-Type", "application/json");

        if(Wxs.config.getIsLog()){
            log.info("打印[请求URL] ->\n{}", req.getUrl());
        }
        // 设置请求Json数据
        if(Strings.isNotBlank(data)){
            req.setData(data);

            if(Wxs.config.getIsLog()){
                log.info("打印[请求参数] ->\n{}", Json.toJson(Lang.map(data)));
            }
        }
        // 获取请求数据
        Response resp = Sender.create(req).send();
        NutMap res = Lang.map(resp.getContent());
        if(Wxs.config.getIsLog()){
            log.info("打印[请求结果] ->\n{}", Json.toJson(res));
        }

        if (!resp.isOK()){
            throw new IllegalArgumentException("resp code=" + resp.getStatus());
        }
        // 拦截异常提示
        if(res.getInt("errcode") != 0){
            throw new RuntimeException("请求微信小程序接口失败,异常代码:" + res.getInt("errcode")+ ",原因:" + res.getString("errmsg"));
        }
        return res;
    }

    /**
     * 构建令牌
     * @return  构建令牌字符串
     */
    public static String buildToken(){
        return String.format("?access_token=%s", Wxs.getToken());
    }


    /**
     * 构建应用密钥
     * @return  构建应用密钥字符串
     */
    public static String buildAppKey(){
        return String.format("appid=%s&secret=%s", Wxs.config.getAppkey(), Wxs.config.getAppsecret());
    }

}
