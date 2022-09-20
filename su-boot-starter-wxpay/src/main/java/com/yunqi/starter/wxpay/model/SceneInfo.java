package com.yunqi.starter.wxpay.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * V3 统一下单-场景信息
 * Created by @author CHQ on 2022/9/18
 */
@Data
@Accessors(chain = true)
public class SceneInfo {

    /**
     * 用户终端IP
     */
    private String payer_client_ip;
    /**
     * 商户端设备号
     */
    private String device_id;
    /**
     * 商户门店信息
     */
    private StoreInfo store_info;
    /**
     * H5 场景信息
     */
    private H5Info h5_info;
}
