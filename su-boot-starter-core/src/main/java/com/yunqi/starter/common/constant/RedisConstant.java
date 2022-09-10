package com.yunqi.starter.common.constant;

/**
 * Redis常量
 * Created by @author CHQ on 2022/9/10
 */
public class RedisConstant {

    public static final String PRE = "sb:";

    /**
     * Token 缓存前缀
     */
    public static final String TOKEN = PRE + "token:";

    /**
     * 订单单号 缓存前缀
     */
    public static final String ORDER = PRE + "order:";

}
