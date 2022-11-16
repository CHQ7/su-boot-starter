package com.yunqi.starter.dingtalk.provider.impl;

import com.yunqi.starter.common.lang.Lang;
import com.yunqi.starter.common.lang.Strings;
import com.yunqi.starter.common.lang.util.NutMap;
import com.yunqi.starter.dingtalk.provider.IDingtalkDepartmentApi;
import com.yunqi.starter.dingtalk.util.DingtalkUtil;
import org.nutz.dao.entity.Record;
import org.nutz.json.Json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by @author CHQ on 2022/11/16
 */
public class DingtalkDepartmentApiImpl implements IDingtalkDepartmentApi {

    @Override
    public List<Record> list() {
        return list(null);
    }

    @Override
    public List<Record> list(String deptId) {
        NutMap data = new NutMap();
        if(Strings.isNotBlank(deptId)){
            data.put("dept_id", deptId);
        }
        NutMap res = DingtalkUtil.post("/topapi/v2/department/listsub" + DingtalkUtil.buildToken(), data);
        return res.getList("result" , Record.class);
    }

    @Override
    public NutMap fetch(String deptId) {
        NutMap data = new NutMap();
        data.put("dept_id", deptId);
        NutMap res = DingtalkUtil.post("/topapi/v2/department/get" + DingtalkUtil.buildToken(), data);
        return Lang.map(res.getString("result"));
    }

    @Override
    public List<String> subIds(String deptId) {
        NutMap data = new NutMap();
        data.put("dept_id", deptId);
        NutMap res = DingtalkUtil.post("/topapi/v2/department/listsubid" + DingtalkUtil.buildToken(), data);
        return new ArrayList<>(Lang.map(res.getString("result")).getList("dept_id_list", String.class));
    }

    @Override
    public List<String> parentIds(String deptId) {
        NutMap data = new NutMap();
        data.put("dept_id", deptId);
        NutMap res = DingtalkUtil.post("/topapi/v2/department/listparentbydept" + DingtalkUtil.buildToken(), data);
        return new ArrayList<>(Lang.map(res.getString("result")).getList("parent_id_list", String.class));
    }

    @Override
    public List<String> userParentIds(String userid) {
        NutMap data = new NutMap();
        data.put("userid", userid);
        NutMap res = DingtalkUtil.post("/topapi/v2/department/listparentbyuser" + DingtalkUtil.buildToken(), data);
        Json.toJson(Lang.map(res.getString("result")).getList("parent_list", String.class).get(0));
        return Lang.map(res.getString("result")).getList("parent_list", NutMap.class).get(0).getList("parent_dept_id_list", String.class);
    }
}
