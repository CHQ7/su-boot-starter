package com.yunqi.starter.dingtalk.provider.impl;

import com.yunqi.starter.common.lang.Lang;
import com.yunqi.starter.dingtalk.provider.IDingtalkProvider;
import com.yunqi.starter.dingtalk.util.DingtalkUtil;
import org.nutz.dao.entity.Record;
import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by @author CHQ on 2022/4/1
 */
public class DingtalkProviderImpl implements IDingtalkProvider {

    @Override
    public NutMap getTicket() {
        return DingtalkUtil.get("/gettoken" + DingtalkUtil.buildAppKey());
    }

    @Override
    public void conversationSend(String sender, String cid, String title, String text) {
        NutMap data = new NutMap();
        data.put("sender", sender);
        data.put("cid", cid);
        data.put("msg", DingtalkUtil.buildMarkdown(title, text));
        DingtalkUtil.post("/message/send_to_conversation" + DingtalkUtil.buildToken(), data);
    }

    @Override
    public void chatSend(String chatId, String title, String text) {
        NutMap data = new NutMap();
        data.addv("chatid", chatId);
        data.addv("msg", DingtalkUtil.buildMarkdown(title, text));
        DingtalkUtil.post("/chat/send" + DingtalkUtil.buildToken(), data);
    }

    @Override
    public void workAllSend(String agentId, String title, String text) {
        NutMap data = new NutMap();
        data.put("agent_id", agentId);
        data.put("msg", DingtalkUtil.buildMarkdown(title, text));
        data.put("to_all_user", true);
        DingtalkUtil.post("/topapi/message/corpconversation/asyncsend_v2" + DingtalkUtil.buildToken(), data);
    }

    @Override
    public void workUserSend(String agentId, String title, String text, String userid_list) {
        NutMap data = new NutMap();
        data.put("agent_id", agentId);
        data.put("msg", DingtalkUtil.buildMarkdown(title, text));
        data.put("userid_list", userid_list);
        DingtalkUtil.post("/topapi/message/corpconversation/asyncsend_v2" + DingtalkUtil.buildToken(), data);
    }

    @Override
    public void workDeptSend(String agentId, String title, String text, String dept_id_list) {
        NutMap data = new NutMap();
        data.put("agent_id", agentId);
        data.put("msg", DingtalkUtil.buildMarkdown(title, text));
        data.put("dept_id_list", dept_id_list);
        DingtalkUtil.post("/topapi/message/corpconversation/asyncsend_v2" + DingtalkUtil.buildToken(), data);
    }

    @Override
    public List<Record> deptList() {
        return deptList(null);
    }

    @Override
    public List<Record> deptList(String deptId) {
        NutMap data = new NutMap();
        if(Strings.isNotBlank(deptId)){
            data.put("dept_id", deptId);
        }
        NutMap res = DingtalkUtil.post("/topapi/v2/department/listsub" + DingtalkUtil.buildToken(), data);
        return res.getList("result" , Record.class);
    }

    @Override
    public NutMap deptByFetch(String deptId) {
        NutMap data = new NutMap();
        data.put("dept_id", deptId);
        NutMap res = DingtalkUtil.post("/topapi/v2/department/get" + DingtalkUtil.buildToken(), data);
        return Lang.map(res.getString("result"));
    }

    @Override
    public List<String> deptSubIds(String deptId) {
        NutMap data = new NutMap();
        data.put("dept_id", deptId);
        NutMap res = DingtalkUtil.post("/topapi/v2/department/listsubid" + DingtalkUtil.buildToken(), data);
        return new ArrayList<>(Lang.map(res.getString("result")).getList("dept_id_list", String.class));
    }

    @Override
    public List<String> deptParentIds(String deptId) {
        NutMap data = new NutMap();
        data.put("dept_id", deptId);
        NutMap res = DingtalkUtil.post("/topapi/v2/department/listparentbydept" + DingtalkUtil.buildToken(), data);
        return new ArrayList<>(Lang.map(res.getString("result")).getList("parent_id_list", String.class));
    }

    @Override
    public List<String> deptUserParentIds(String userid) {
        NutMap data = new NutMap();
        data.put("userid", userid);
        NutMap res = DingtalkUtil.post("/topapi/v2/department/listparentbyuser" + DingtalkUtil.buildToken(), data);
        Json.toJson(Lang.map(res.getString("result")).getList("parent_list", String.class).get(0));
        return Lang.map(res.getString("result")).getList("parent_list", NutMap.class).get(0).getList("parent_dept_id_list", String.class);
    }

}
