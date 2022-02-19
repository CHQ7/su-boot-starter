package com.yunqi.starter.common.utils;

import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.json.Json;
import org.nutz.lang.Encoding;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.Mvcs;

/**
 * 获取IP归属地工具类
 * Created by @author JsckChin on 2022/1/22
 */
public class IPUtil {

    /**
     * 太平洋网络IP归属地接口
     */
    public static final String IP_WHOIS_URL =  "http://whois.pconline.com.cn/ipJson.jsp?ip=%s&json=true";

    public static String getIPAddress(String ip){
        return getString(ip);
    }

    public static String getIPAddress(){
        String ip = Lang.getIP(Mvcs.getReq());
        return getString(ip);
    }

    private static String getString(String ip) {
        Response resp = Http.get(String.format(IP_WHOIS_URL, ip));
        if (!resp.isOK()) {
            throw new IllegalStateException("postPay with SSL, resp code=" + resp.getStatus());
        }
        NutMap map= Json.fromJson(NutMap.class, resp.getContent(Encoding.GBK));
        return map.getString("addr");
    }
}
