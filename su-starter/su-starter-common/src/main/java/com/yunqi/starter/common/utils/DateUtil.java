package com.yunqi.starter.common.utils;

/**
 * 时间工具类
 * Created by @author JsckChin on 2022/1/28
 */
public class DateUtil extends cn.hutool.core.date.DateUtil {

    public static void main(String[] args) {
        System.out.println("当前时间字符串:" + DateUtil.now());
        System.out.println("当前日期字符串:" + DateUtil.today());
        System.out.println("当前时间的时间戳:" + DateUtil.current());
        System.out.println("当前时间的时间戳秒:" + DateUtil.currentSeconds());
    }
}
