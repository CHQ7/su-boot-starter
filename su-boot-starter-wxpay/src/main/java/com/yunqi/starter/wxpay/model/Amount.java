package com.yunqi.starter.wxpay.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * V3 统一下单-订单金额
 * Created by @author CHQ on 2022/9/18
 */
@Data
@Accessors(chain = true)
public class Amount {

    /**
     * 总金额
     */
    private int total;
    /**
     * 货币类型
     */
    private String currency;
}
