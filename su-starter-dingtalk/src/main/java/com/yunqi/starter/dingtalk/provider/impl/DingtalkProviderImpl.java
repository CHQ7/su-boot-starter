package com.yunqi.starter.dingtalk.provider.impl;

import com.yunqi.starter.dingtalk.provider.IDingtalkProvider;
import com.yunqi.starter.dingtalk.util.DingtalkUtil;
import org.nutz.lang.util.NutMap;

/**
 * Created by @author JsckChin on 2022/4/1
 */
public class DingtalkProviderImpl implements IDingtalkProvider {

    @Override
    public NutMap getTicket() {
        return DingtalkUtil.get("/gettoken" + DingtalkUtil.buildAppKey());
    }

    @Override
    public void send(String sender, String cid, String title, String text) {
        NutMap data = new NutMap();
        data.put("sender", sender);
        data.put("cid", cid);
        data.put("msg", DingtalkUtil.buildMarkdown(title, text));
        DingtalkUtil.post("/message/send_to_conversation" + DingtalkUtil.buildToken(), data);
    }

    @Override
    public void chatSend(String chatId, String title, String text) {
        DingtalkUtil.post("/chat/send" + DingtalkUtil.buildToken(), DingtalkUtil.buildChatMarkdownData(chatId, title, text));
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

}
