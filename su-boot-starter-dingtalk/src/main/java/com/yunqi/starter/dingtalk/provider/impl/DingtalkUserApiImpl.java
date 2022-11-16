package com.yunqi.starter.dingtalk.provider.impl;

import com.yunqi.starter.common.lang.Lang;
import com.yunqi.starter.common.lang.Strings;
import com.yunqi.starter.common.lang.util.NutMap;
import com.yunqi.starter.dingtalk.provider.IDingtalkUserApi;
import com.yunqi.starter.dingtalk.util.DingtalkUtil;

import java.util.List;

/**
 * Created by @author CHQ on 2022/11/16
 */
public class DingtalkUserApiImpl implements IDingtalkUserApi {


    @Override
    public String fetchByMobile(String mobile) {
        NutMap data = new NutMap();
        if(Strings.isNotBlank(mobile)){
            data.put("mobile", mobile);
        }
        NutMap res = DingtalkUtil.post("/topapi/v2/user/getbymobile" + DingtalkUtil.buildToken(), data);
        return Lang.map(res.getString("result")).getString("userid");
    }

    @Override
    public NutMap fetch(String userId) {
        NutMap data = new NutMap();
        if(Strings.isNotBlank(userId)){
            data.put("userid", userId);
        }
        NutMap res = DingtalkUtil.post("/topapi/v2/user/get" + DingtalkUtil.buildToken(), data);
        return Lang.map(res.getString("result"));
    }

    @Override
    public NutMap fetchByUnionid(String unionid) {
        NutMap data = new NutMap();
        if(Strings.isNotBlank(unionid)){
            data.put("unionid", unionid);
        }
        NutMap res = DingtalkUtil.post("/topapi/user/getbyunionid" + DingtalkUtil.buildToken(), data);
        return Lang.map(res.getString("result"));
    }

    @Override
    public List<String> listByIds(String deptId) {
        NutMap data = new NutMap();
        if(Strings.isNotBlank(deptId)){
            data.put("dept_id", deptId);
        }
        NutMap res = DingtalkUtil.post("/topapi/user/listid" + DingtalkUtil.buildToken(), data);
        return Lang.map(res.getString("result")).getList("userid_list", String.class);
    }

    @Override
    public NutMap deptSimpleList(String deptId, String page, String pageSize) {
        return deptSimpleList(deptId, page, pageSize, null);
    }

    @Override
    public NutMap deptSimpleList(String deptId, String page, String pageSize,String sort) {
        NutMap data = new NutMap();
        if(Strings.isNotBlank(deptId)){
            data.put("dept_id", deptId);
        }
        if(Strings.isNotBlank(page)){
            data.put("cursor", page);
        }
        if(Strings.isNotBlank(pageSize)){
            data.put("size", pageSize);
        }
        if(Strings.isNotBlank(sort)){
            data.put("order_field", sort);
        }
        NutMap res = DingtalkUtil.post("/topapi/user/listsimple" + DingtalkUtil.buildToken(), data);
        return Lang.map(res.getString("result"));
    }

    @Override
    public NutMap deptList(String deptId, String page, String pageSize) {
        return deptList(deptId, page, pageSize, null );
    }

    @Override
    public NutMap deptList(String deptId, String page, String pageSize, String sort) {
        NutMap data = new NutMap();
        if(Strings.isNotBlank(deptId)){
            data.put("dept_id", deptId);
        }
        if(Strings.isNotBlank(page)){
            data.put("cursor", page);
        }
        if(Strings.isNotBlank(pageSize)){
            data.put("size", pageSize);
        }
        if(Strings.isNotBlank(sort)){
            data.put("order_field", sort);
        }
        NutMap res = DingtalkUtil.post("/topapi/v2/user/list" + DingtalkUtil.buildToken(), data);
        return Lang.map(res.getString("result"));
    }

}
