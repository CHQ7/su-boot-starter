package com.yunqi.starter.database.service;


import org.nutz.dao.Dao;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import javax.annotation.Resource;

/**
 * 实体Service抽象类. 属于辅助类. 任何方法被调用前,必须确保Dao实例已经传入
 * Created by @author JsckChin on 2022/1/29
 */
public class EntityService <T> extends org.nutz.service.EntityService<T> {

    public static final String EQ = "=";
    public static final String NEQ = "!=";
    public static final String LT = "<";
    public static final String GT = ">";
    public static final String IS = "is";
    public static final String IS_NOT = "is not";
    public static final String NOT = "not";
    public static final String LIKE = "like";
    public static final String IN = "in";
    public static final String LT_AND_EQ = "=<";
    public static final String GT_AND_EQ = ">=";

    protected Log logger = Logs.get();

    @Resource(type = Dao.class)
    public void init(Dao dao) {
        super.setDao(dao);
    }

}
