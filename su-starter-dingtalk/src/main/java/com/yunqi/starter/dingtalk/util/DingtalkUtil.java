package com.yunqi.starter.dingtalk.util;

import com.yunqi.starter.common.lang.Lang;
import com.yunqi.starter.common.lang.Strings;
import com.yunqi.starter.dingtalk.spi.Dingtalks;
import lombok.extern.slf4j.Slf4j;
import org.nutz.http.Request;
import org.nutz.http.Response;
import org.nutz.http.Sender;
import org.nutz.json.Json;
import org.nutz.lang.util.NutMap;

/**
 * 钉钉内部工具类
 * Created by @author JsckChin on 2022/4/1
 */
@Slf4j
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
        return call(req);
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

    /**
     * POST请求
     * @param url    请求地址
     * @param data   请求数据
     * @return       map
     */
    public static NutMap post(String url, String data){
        log.warn("url: {}", Dingtalks.config.getDomain() + url);
        // 发起网络请求
        Request req = Request.create(Dingtalks.config.getDomain() + url, Request.METHOD.POST);

        // 设置Json请求格式
        req.getHeader().set("Content-Type", "application/json");
        // 设置请求Json数据
        if(Strings.isNotBlank(data)){
            req.setData(data);
        }
        return call(req);
    }

    public static NutMap call(Request req){
        // 获取请求数据
        Response resp = Sender.create(req).send();
        if (!resp.isOK()){
            throw new IllegalArgumentException("resp code=" + resp.getStatus());
        }
        // 拦截异常提示
        NutMap res = Lang.map(resp.getContent());
        log.warn("打印 {}", Json.toJson(res));
        if(res.getInt("errcode") != 0){
            throw new RuntimeException("请求钉钉接口失败,异常代码:" + res.getInt("errcode")+ ",原因:" + res.getString("errmsg"));
        }
        return res;
    }

    public static String buildToken(){
        return String.format("?access_token=%s", Dingtalks.getToken());
    }

    public static String buildAppKey(){
        return String.format("?appkey=%s&appsecret=%s", Dingtalks.config.getAppkey(), Dingtalks.config.getAppsecret());
    }

    /**
     * 构建Markdown格式信息体数据
     * @param title     标题
     * @param text      内容
     * @return          Json格式信息体
     */
    public static NutMap buildMarkdown(String title,String text) {
        NutMap map = new NutMap();
        map.put("title", title);
        map.put("text", text);
        NutMap reqMap = new NutMap();
        reqMap.put("markdown", map);
        reqMap.put("msgtype", "markdown");
        return reqMap;
    }

    /**
     * 钉钉群 markdown类型
     * @param chatId    钉钉群ID
     * @param title     标题
     * @param text      内容
     * @return          Json字符串
     */
    public static String buildChatMarkdownData(String chatId, String title,String text) {
        NutMap map = new NutMap();
        map.addv("chatid", chatId);
        map.addv("msg", buildMarkdown(title, text));
        return Json.toJson(map);
    }


}
