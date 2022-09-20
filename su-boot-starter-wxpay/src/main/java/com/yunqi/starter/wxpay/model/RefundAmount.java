package com.yunqi.starter.wxpay.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * V3 微信申请退款-金额信息
 * Created by @author CHQ on 2022/9/18
 */
@Data
@Accessors(chain = true)
public class RefundAmount {

    /**
     * 总金额
     */
    private int total;
    /**
     * 货币类型
     */
    private String currency;
    /**
     * 退款金额
     */
    private int refund;
}
