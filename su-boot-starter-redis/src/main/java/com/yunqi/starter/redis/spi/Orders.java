package com.yunqi.starter.redis.spi;

import cn.hutool.core.date.DateUtil;
import com.yunqi.starter.common.constant.RedisConstant;

/**
 * 订单工具类
 * Created by @author CHQ on 2022/9/10
 */
public class Orders {

    /**
     * 订单单号生成
     * <br>
     * 生成14位订单编号:8位日期+6位以上自增id
     * @param tab       标记位
     * @return          订单号：20220911000006
     */
    public static String generateOrderSn(String tab){
        String date = DateUtil.format(DateUtil.date(), "yyyyMMdd");

        StringBuilder sb = new StringBuilder();
        sb.append(date);

        // 获取Redis自增ID
        String key = RedisConstant.ORDER + tab + ":" +  date;
        Long incr = RedisCaches.incr(key, 1);

        String incrStr = incr.toString();
        if (incrStr.length() <= 6) {
            sb.append(String.format("%06d", incr));
        } else {
            sb.append(incrStr);
        }
        return sb.toString();
    }

}
