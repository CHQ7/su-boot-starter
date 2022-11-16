package com.yunqi.starter.dingtalk.provider.impl;

import com.yunqi.starter.common.lang.util.NutMap;
import com.yunqi.starter.dingtalk.provider.IDingtalkMessageApi;
import com.yunqi.starter.dingtalk.util.DingtalkUtil;

/**
 * Created by @author CHQ on 2022/11/16
 */
public class DingtalkMessageApiImpl implements IDingtalkMessageApi {

    @Override
    public void conversationSend(String sender, String chatId, String title, String text) {
        NutMap data = new NutMap();
        data.put("sender", sender);
        data.put("cid", chatId);
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
    public void send(String agentId, String title, String text) {
        NutMap data = new NutMap();
        data.put("agent_id", agentId);
        data.put("msg", DingtalkUtil.buildMarkdown(title, text));
        data.put("to_all_user", true);
        DingtalkUtil.post("/topapi/message/corpconversation/asyncsend_v2" + DingtalkUtil.buildToken(), data);
    }

    @Override
    public void userSend(String agentId, String title, String text, String userid_list) {
        NutMap data = new NutMap();
        data.put("agent_id", agentId);
        data.put("msg", DingtalkUtil.buildMarkdown(title, text));
        data.put("userid_list", userid_list);
        DingtalkUtil.post("/topapi/message/corpconversation/asyncsend_v2" + DingtalkUtil.buildToken(), data);
    }

    @Override
    public void deptSend(String agentId, String title, String text, String dept_id_list) {
        NutMap data = new NutMap();
        data.put("agent_id", agentId);
        data.put("msg", DingtalkUtil.buildMarkdown(title, text));
        data.put("dept_id_list", dept_id_list);
        DingtalkUtil.post("/topapi/message/corpconversation/asyncsend_v2" + DingtalkUtil.buildToken(), data);
    }
}
