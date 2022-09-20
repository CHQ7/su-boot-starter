package com.yunqi.starter.wxpay.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * V3 微信申请退款-退款商品
 * Created by @author CHQ on 2022/9/18
 */
@Data
@Accessors(chain = true)
public class RefundGoodsDetail {

    /**
     * 商户侧商品编码
     */
    private String merchant_goods_id;
    /**
     * 微信侧商品编码
     */
    private String wechatpay_goods_id;
    /**
     * 商品名称
     */
    private String goods_name;
    /**
     * 商品单价
     */
    private int unit_price;
    /**
     * 商品退款金额
     */
    private int refund_amount;
    /**
     * 商品退货数量
     */
    private int refund_quantity;
}
