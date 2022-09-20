package com.yunqi.starter.wxpay.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * V3 统一下单-结算信息
 * Created by @author CHQ on 2022/9/18
 */
@Data
@Accessors(chain = true)
public class SettleInfo {

    /**
     * 是否指定分账
     */
    private boolean profit_sharing;
    /**
     * 补差金额
     */
    private int subsidy_amount;

}
