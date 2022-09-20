package com.yunqi.starter.wxpay.provider;

import com.yunqi.starter.wxpay.model.WxPayResponse;

/**
 * 微信支付V3 API
 * Created by @author CHQ on 2022/9/20
 */
public interface WxPay3Api {

    /**
     * 通过V3 API证书和商户号获取平台证书,需要解密
     *
     * @return              WxPayResponse
     * @throws Exception    Exception
     */
    WxPayResponse certificates() throws Exception;


    /**
     * JSAPI/小程序下单
     *
     * <br>
     * body json <a href="https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/pay/transactions/chapter3_2.shtml">参数详见</a>
     *
     * @param body          body参数
     * @return              WxPayResponse
     * @throws Exception    Exception
     */
    WxPayResponse order_jsapi(String body) throws Exception;


    /**
     * APP下单
     *
     * <br>
     * body json  <a href="https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/pay/transactions/chapter3_1.shtml">参数详见</a>
     *
     * @param body          body参数
     * @return              WxPayResponse
     * @throws Exception    Exception
     */
    WxPayResponse order_app(String body) throws Exception;

    /**
     * Native下单
     *
     * <br>
     * body json  <a href="https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/pay/transactions/chapter3_3.shtml">参数详见</a>
     *
     * @param body          body参数
     * @return              WxPayResponse
     * @throws Exception    Exception
     */
    WxPayResponse order_native(String body) throws Exception;

    /**
     * H5下单
     *
     * <br>
     * body json  <a href="https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/pay/transactions/chapter3_4.shtml">参数详见</a>
     *
     * @param body          body参数
     * @return              WxPayResponse
     * @throws Exception    Exception
     */
    WxPayResponse order_h5(String body) throws Exception;

    /**
     * 关闭订单
     *
     * @param out_trade_no  商户订单号
     * @return              WxPayResponse
     * @throws Exception    Exception
     */
    WxPayResponse order_close(String out_trade_no) throws Exception;

    /**
     * 微信支付订单号查询
     *
     * @param transaction_id    微信支付订单号
     * @return                  WxPayResponse
     * @throws Exception        Exception
     */
    WxPayResponse order_query_transaction_id(String transaction_id) throws Exception;

    /**
     * 商户订单号查询
     *
     * @param out_trade_no  商户订单号
     * @return              WxPayResponse
     * @throws Exception    Exception
     */
    WxPayResponse order_query_out_trade_no(String out_trade_no) throws Exception;

    /**
     * 申请退款
     *
     * @param body          body参数
     * @return              WxPayResponse
     * @throws Exception    Exception
     */
    WxPayResponse ecommerce_refunds_apply(String body) throws Exception;


    /**
     * 退款查询
     *
     * @param refund_id     微信退款ID
     * @return              WxPayResponse
     * @throws Exception    Exception
     */
    WxPayResponse ecommerce_refunds_query_refund_id(String refund_id) throws Exception;


    /**
     * 退款查询
     *
     * @param out_refund_no     微信退款单号
     * @return                  WxPayResponse
     * @throws Exception        Exception
     */
    WxPayResponse ecommerce_refunds_query_out_refund_no(String out_refund_no) throws Exception;


}
