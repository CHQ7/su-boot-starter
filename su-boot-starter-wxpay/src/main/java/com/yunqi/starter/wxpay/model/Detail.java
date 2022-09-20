package com.yunqi.starter.wxpay.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * V3 统一下单-优惠功能
 * Created by @author CHQ on 2022/9/18
 */
@Data
@Accessors(chain = true)
public class Detail {

    /**
     * 订单原价
     */
    private int cost_price;
    /**
     * 商品小票ID
     */
    private String invoice_id;
    /**
     * 单品列表
     */
    private List<GoodsDetail> goods_detail;
}
