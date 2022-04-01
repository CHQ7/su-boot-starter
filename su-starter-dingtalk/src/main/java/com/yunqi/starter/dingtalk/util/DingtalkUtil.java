package com.yunqi.starter.dingtalk.util;

import com.yunqi.starter.common.lang.Lang;
import com.yunqi.starter.common.lang.Strings;
import com.yunqi.starter.dingtalk.spi.Dingtalks;
import org.nutz.http.Request;
import org.nutz.http.Response;
import org.nutz.http.Sender;
import org.nutz.json.Json;
import org.nutz.lang.util.NutMap;

/**
 * 钉钉内部工具类
 * Created by @author JsckChin on 2022/4/1
 */
public class DingtalkUtil {

    /**
     * GET请求
     * @param url 请求地址
     * @return NutMap
     */
    public static NutMap get(String url){
        // 发起网络请求
        Request req = Request.create(Dingtalks.config.getDomain() + url, Request.METHOD.GET);

        // 设置Json请求格式
        req.getHeader().set("Content-Type", "application/json");

        // 获取请求数据
        Response resp = Sender.create(req).send();
        if (!resp.isOK()){
            throw new IllegalArgumentException("resp code=" + resp.getStatus());
        }
        // 拦截异常提示
        NutMap res = Lang.map(resp.getContent());
        if(res.getInt("errcode") != 0){
            throw new RuntimeException("请求钉钉接口失败,异常代码:" + res.getInt("errcode")+ ",原因:" + res.getString("errmsg"));
        }
        return res;
    }

    /**
     * POST请求
     * @param url    请求地址
     * @param params 请求参数
     * @param data   请求数据
     * @return NutMap
     */
    public static NutMap post(String url, NutMap params, String data){
        // 发起网络请求
        Request req = Request.create(Dingtalks.config.getDomain() + url, Request.METHOD.POST, params);

        // 设置Json请求格式
        req.getHeader().set("Content-Type", "application/json");
        // 设置请求Json数据
        if(Strings.isNotBlank(data)){
            req.setData(data);
        }
        // 获取请求数据
        Response resp = Sender.create(req).send();
        if (!resp.isOK()){
            throw new IllegalArgumentException("resp code=" + resp.getStatus());
        }
        // 拦截异常提示
        NutMap res = Lang.map(resp.getContent());
        if(res.getInt("errcode") != 0){
            throw new RuntimeException("请求钉钉接口失败,异常代码:" + res.getInt("errcode")+ ",原因:" + res.getString("errmsg"));
        }
        return res;
    }

}
