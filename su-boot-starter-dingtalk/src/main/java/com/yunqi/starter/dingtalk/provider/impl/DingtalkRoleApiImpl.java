package com.yunqi.starter.dingtalk.provider.impl;

import com.yunqi.starter.common.lang.Lang;
import com.yunqi.starter.common.lang.Strings;
import com.yunqi.starter.common.lang.util.NutMap;
import com.yunqi.starter.dingtalk.provider.IDingtalkRoleApi;
import com.yunqi.starter.dingtalk.util.DingtalkUtil;
import org.nutz.dao.entity.Record;

import java.util.List;

/**
 * Created by @author CHQ on 2022/11/16
 */
public class DingtalkRoleApiImpl implements IDingtalkRoleApi {

    @Override
    public NutMap List2(String page, String pageSize) {
        NutMap data = new NutMap();
        if(Strings.isNotBlank(page)){
            data.put("offset", page);
        }
        if(Strings.isNotBlank(pageSize)){
            data.put("size", pageSize);
        }
        NutMap res = DingtalkUtil.post("/topapi/role/list" + DingtalkUtil.buildToken(), data);
        return Lang.map(res.getString("result"));
    }

    @Override
    public List<Record> list() {
        return list(null, null);
    }

    @Override
    public List<Record> list(String page, String pageSize) {
        return List2(page, pageSize).getList("list", Record.class);
    }

    @Override
    public NutMap group(String groupId) {
        NutMap data = new NutMap();
        if(Strings.isNotBlank(groupId)){
            data.put("group_id", groupId);
        }
        NutMap res = DingtalkUtil.post("/topapi/role/getrolegroup" + DingtalkUtil.buildToken(), data);
        return Lang.map(res.getString("role_group"));
    }


    @Override
    public NutMap fetch(String roleId) {
        NutMap data = new NutMap();
        if(Strings.isNotBlank(roleId)){
            data.put("roleId", roleId);
        }
        NutMap res = DingtalkUtil.post("/topapi/role/getrole" + DingtalkUtil.buildToken(), data);
        return Lang.map(res.getString("role"));
    }

    @Override
    public NutMap simpleUserList(String roleId, String page, String pageSize) {
        NutMap data = new NutMap();
        if(Strings.isNotBlank(roleId)){
            data.put("role_id", roleId);
        }
        if(Strings.isNotBlank(page)){
            data.put("offset", page);
        }
        if(Strings.isNotBlank(pageSize)){
            data.put("size", pageSize);
        }
        NutMap res = DingtalkUtil.post("/topapi/role/simplelist" + DingtalkUtil.buildToken(), data);
        return Lang.map(res.getString("result"));
    }

}
