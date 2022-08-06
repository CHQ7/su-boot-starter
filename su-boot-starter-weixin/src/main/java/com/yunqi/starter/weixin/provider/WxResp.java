package com.yunqi.starter.weixin.provider;

import com.yunqi.starter.common.lang.util.NutMap;

/**
 * Created by @author CHQ on 2022/7/27
 */
public class WxResp extends NutMap {

    private static final long serialVersionUID = -1;

    public int errcode() {
        return getInt("errcode", 0);
    }

    public String errmsg() {
        return getString("errmsg");
    }


}
