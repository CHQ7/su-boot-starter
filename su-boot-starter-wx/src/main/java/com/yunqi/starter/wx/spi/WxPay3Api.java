package com.yunqi.starter.wx.spi;

import com.yunqi.starter.common.json.Json;
import com.yunqi.starter.wx.model.WxPayResponse;
import com.yunqi.starter.wx.util.WxPayUtil;
import org.nutz.http.Request;
import org.nutz.http.Response;
import org.nutz.http.Sender;
import org.nutz.lang.Strings;
import org.nutz.lang.random.R;
import org.nutz.lang.util.NutMap;

/**
 * 微信支付V3 API
 * Created by @author CHQ on 2022/9/17
 */
public class WxPay3Api {

    public static String PAY_DOMAIN = "https://api.mch.weixin.qq.com";

    /**
     * 通过V3 API证书和商户号获取平台证书
     *
     * @param mchId         商户号
     * @param serialNo      V3API证书序列号
     * @param keyPath       V3Key证书文件路径
     * @return              WxPayResponse
     * @throws Exception    Exception
     */
    public static WxPayResponse certificates(String mchId, String serialNo, String keyPath) throws Exception {
        String url = "/v3/certificates";
        long timestamp = System.currentTimeMillis() / 1000;
        String authType = "WECHATPAY2-SHA256-RSA2048";
        String nonceStr = R.UU32().toUpperCase();
        return call("GET", url, mchId, serialNo, null, keyPath, "", nonceStr, timestamp, authType);
    }

    /**
     * JSAPI/小程序下单
     *
     * <br>
     * body json <a href="https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/pay/transactions/chapter3_2.shtml">参数详见</a>
     *
     * @param mchId         商户号
     * @param serialNo      V3API证书序列号
     * @param keyPath       V3Key证书文件路径
     * @param body          body参数
     * @return              WxPayResponse
     * @throws Exception    Exception
     */
    public static WxPayResponse order_jsapi(String mchId, String serialNo, String keyPath, String body) throws Exception {
        String url = "/v3/pay/transactions/jsapi";
        long timestamp = System.currentTimeMillis() / 1000;
        String authType = "WECHATPAY2-SHA256-RSA2048";
        String nonceStr = R.UU32().toUpperCase();
        return call("POST", url, mchId, serialNo, null, keyPath, body, nonceStr, timestamp, authType);
    }

    /**
     * APP下单
     *
     * <br>
     * body json  <a href="https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/pay/transactions/chapter3_1.shtml">参数详见</a>
     *
     * @param mchId         商户号
     * @param serialNo      V3API证书序列号
     * @param keyPath       V3Key证书文件路径
     * @param body          body参数
     * @return              WxPayResponse
     * @throws Exception    Exception
     */
    public static WxPayResponse order_app(String mchId, String serialNo, String keyPath, String body) throws Exception {
        String url = "/v3/pay/transactions/app";
        long timestamp = System.currentTimeMillis() / 1000;
        String authType = "WECHATPAY2-SHA256-RSA2048";
        String nonceStr = R.UU32().toUpperCase();
        return call("POST", url, mchId, serialNo, null, keyPath, body, nonceStr, timestamp, authType);
    }

    /**
     * Native下单
     *
     * <br>
     * body json  <a href="https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/pay/transactions/chapter3_3.shtml">参数详见</a>
     *
     * @param mchId         商户号
     * @param serialNo      V3API证书序列号
     * @param keyPath       V3Key证书文件路径
     * @param body          body参数
     * @return              WxPayResponse
     * @throws Exception    Exception
     */
    public static WxPayResponse order_native(String mchId, String serialNo, String keyPath, String body) throws Exception {
        String url = "/v3/pay/transactions/native";
        long timestamp = System.currentTimeMillis() / 1000;
        String authType = "WECHATPAY2-SHA256-RSA2048";
        String nonceStr = R.UU32().toUpperCase();
        return call("POST", url, mchId, serialNo, null, keyPath, body, nonceStr, timestamp, authType);
    }

    /**
     * H5下单
     *
     * <br>
     * body json  <a href="https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/pay/transactions/chapter3_4.shtml">参数详见</a>
     *
     * @param mchId         商户号
     * @param serialNo      V3API证书序列号
     * @param keyPath       V3Key证书文件路径
     * @param body          body参数
     * @return              WxPayResponse
     * @throws Exception    Exception
     */
    public static WxPayResponse order_h5(String mchId, String serialNo, String keyPath, String body) throws Exception {
        String url = "/v3/pay/transactions/h5";
        long timestamp = System.currentTimeMillis() / 1000;
        String authType = "WECHATPAY2-SHA256-RSA2048";
        String nonceStr = R.UU32().toUpperCase();
        return call("POST", url, mchId, serialNo, null, keyPath, body, nonceStr, timestamp, authType);
    }

    /**
     * 关闭订单
     *
     * @param mchId         商户号
     * @param serialNo      V3API证书序列号
     * @param keyPath       V3Key证书文件路径
     * @param out_trade_no  商户订单号
     * @return              WxPayResponse
     * @throws Exception    Exception
     */
    public static WxPayResponse order_close(String mchId, String serialNo, String keyPath, String out_trade_no) throws Exception {
        String url = "/v3/pay/transactions/out-trade-no/" + out_trade_no + "/close";
        long timestamp = System.currentTimeMillis() / 1000;
        String body = Json.toJson(NutMap.NEW().addv("mchid", mchId));
        String authType = "WECHATPAY2-SHA256-RSA2048";
        String nonceStr = R.UU32().toUpperCase();
        return call("POST", url, mchId, serialNo, null, keyPath, body, nonceStr, timestamp, authType);
    }

    /**
     * 微信支付订单号查询
     *
     * @param mchId             商户号
     * @param serialNo          V3API证书序列号
     * @param keyPath           V3Key证书文件路径
     * @param transaction_id    微信支付订单号
     * @return                  WxPayResponse
     * @throws Exception        Exception
     */
    public static WxPayResponse order_query_transaction_id(String mchId, String serialNo, String keyPath, String transaction_id) throws Exception {
        String url = "/v3/pay/transactions/id/" + transaction_id;
        long timestamp = System.currentTimeMillis() / 1000;
        String authType = "WECHATPAY2-SHA256-RSA2048";
        String nonceStr = R.UU32().toUpperCase();
        return call("GET", url, mchId, serialNo, null, keyPath, "", nonceStr, timestamp, authType);
    }

    /**
     * 商户订单号查询
     *
     * @param mchId         商户号
     * @param serialNo      V3API证书序列号
     * @param keyPath       V3Key证书文件路径
     * @param out_trade_no  商户订单号
     * @return              WxPayResponse
     * @throws Exception    Exception
     */
    public static WxPayResponse order_query_out_trade_no(String mchId, String serialNo, String keyPath, String out_trade_no) throws Exception {
        String url = "/v3/pay/transactions/out_trade_no/" + out_trade_no + "?mchid=" + mchId;
        long timestamp = System.currentTimeMillis() / 1000;
        String authType = "WECHATPAY2-SHA256-RSA2048";
        String nonceStr = R.UU32().toUpperCase();
        return call("GET", url, mchId, serialNo, null, keyPath, "", nonceStr, timestamp, authType);
    }

    /**
     * 申请退款
     *
     * @param mchId         商户号
     * @param serialNo      V3API证书序列号
     * @param keyPath       V3Key证书文件路径
     * @param body          body参数
     * @return              WxPayResponse
     * @throws Exception    Exception
     */
    public static WxPayResponse ecommerce_refunds_apply(String mchId, String serialNo, String keyPath, String body) throws Exception {
        String url = "/v3/ecommerce/refunds/apply";
        long timestamp = System.currentTimeMillis() / 1000;
        String authType = "WECHATPAY2-SHA256-RSA2048";
        String nonceStr = R.UU32().toUpperCase();
        return call("POST", url, mchId, serialNo, null, keyPath, body, nonceStr, timestamp, authType);
    }

    /**
     * 退款查询
     *
     * @param mchId         商户号
     * @param serialNo      V3API证书序列号
     * @param keyPath       V3Key证书文件路径
     * @param refund_id     微信退款ID
     * @return              WxPayResponse
     * @throws Exception    Exception
     */
    public static WxPayResponse ecommerce_refunds_query_refund_id(String mchId, String serialNo, String keyPath, String refund_id) throws Exception {
        String url = "/v3/ecommerce/refunds/id/" + refund_id + "?sub_mchid=" + mchId;
        long timestamp = System.currentTimeMillis() / 1000;
        String authType = "WECHATPAY2-SHA256-RSA2048";
        String nonceStr = R.UU32().toUpperCase();
        return call("GET", url, mchId, serialNo, null, keyPath, "", nonceStr, timestamp, authType);
    }

    /**
     * 退款查询
     *
     * @param mchId             商户号
     * @param serialNo          V3API证书序列号
     * @param keyPath           V3Key证书文件路径
     * @param out_refund_no     微信退款单号
     * @return                  WxPayResponse
     * @throws Exception        Exception
     */
    public static WxPayResponse ecommerce_refunds_query_out_refund_no(String mchId, String serialNo, String keyPath, String out_refund_no) throws Exception {
        String url = "/v3/ecommerce/refunds/out-refund-no/" + out_refund_no + "?sub_mchid=" + mchId;
        long timestamp = System.currentTimeMillis() / 1000;
        String authType = "WECHATPAY2-SHA256-RSA2048";
        String nonceStr = R.UU32().toUpperCase();
        return call("GET", url, mchId, serialNo, null, keyPath, "", nonceStr, timestamp, authType);
    }

    /**
     * 发起http请求
     *
     * @param method        请求方法
     * @param url           URL后缀
     * @param mchId         商户号
     * @param serialNo      V3API序列号
     * @param platSerialNo  平台证书序列号
     * @param keyPath       V3Key证书文件路径
     * @param body          传参
     * @param nonceStr      随机字符串
     * @param timestamp     时间戳
     * @param authType      认证类型
     * @return              WxPayResponse
     * @throws Exception    Exception
     */
    public static WxPayResponse call(String method, String url, String mchId,
                                     String serialNo, String platSerialNo, String keyPath,
                                     String body, String nonceStr, long timestamp,
                                     String authType) throws Exception {
        String authorization = WxPayUtil.buildAuthorization(method, url, mchId, serialNo,
                keyPath, body, nonceStr, timestamp, authType);
        if (Strings.isBlank(platSerialNo)) {
            platSerialNo = serialNo;
        }
        if ("GET".equals(method)) {
            return call_get(PAY_DOMAIN + url, authorization, platSerialNo);
        }
        if ("POST".equals(method)) {
            return call_post(PAY_DOMAIN + url, authorization, platSerialNo, body);
        }
        return null;
    }

    /**
     * get请求
     *
     * @param url               URL后缀
     * @param authorization     authorization
     * @param serialNumber      平台证书序列号
     * @return                  WxPayResponse
     */
    public static WxPayResponse call_get(String url, String authorization, String serialNumber) {
        Request req = Request.create(url, Request.METHOD.GET);
        req.getHeader().addAll(WxPayUtil.getHeaders(authorization, serialNumber));
        Sender sender = Sender.create(req);
        Response resp = sender.send();
        if (!resp.isOK())
            throw new IllegalStateException("resp code=" + resp.getStatus());
        WxPayResponse wxPayResponse = new WxPayResponse();
        wxPayResponse.setBody(resp.getContent("UTF-8"));
        wxPayResponse.setHeader(resp.getHeader());
        wxPayResponse.setStatus(resp.getStatus());
        return wxPayResponse;
    }

    /**
     * post请求
     *
     * @param url               URL后缀
     * @param authorization     authorization
     * @param serialNumber      平台证书序列号
     * @param body              body参数
     * @return                  WxPayResponse
     */
    public static WxPayResponse call_post(String url, String authorization, String serialNumber, String body) {
        Request req = Request.create(url, Request.METHOD.POST);
        req.setData(body);
        req.getHeader().addAll(WxPayUtil.getHeaders(authorization, serialNumber));
        Sender sender = Sender.create(req);
        Response resp = sender.send();
        if (!resp.isOK())
            throw new IllegalStateException("resp code=" + resp.getStatus() + ",msg=" + resp.getContent());
        WxPayResponse wxPayResponse = new WxPayResponse();
        wxPayResponse.setBody(resp.getContent("UTF-8"));
        wxPayResponse.setHeader(resp.getHeader());
        wxPayResponse.setStatus(resp.getStatus());
        return wxPayResponse;
    }
}
