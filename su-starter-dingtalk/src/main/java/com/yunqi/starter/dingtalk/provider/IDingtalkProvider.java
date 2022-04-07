package com.yunqi.starter.dingtalk.provider;

import org.nutz.lang.util.NutMap;

/**
 * 钉钉接口
 * Created by @author JsckChin on 2022/4/1
 */
public interface IDingtalkProvider {

    /**
     * 获取凭证
     * @return 请求数据
     */
    NutMap getTicket();

    // =================== 消息通知Api相关 begin ===================

    /**
     * 消息通知 -> 发送普通消息
     * @param sender    消息发送者的userid
     * @param cid       群会话或者个人会话的id
     * @param title     消息标题
     * @param text      消息内容
     */
    void send(String sender, String cid, String title,String text);

    /**
     * 消息通知 -> 发送消息到企业群
     * @param chatId    企业群会话的ID
     * @param title     消息标题
     * @param text      消息内容
     */
    void chatSend(String chatId, String title,String text);

    /**
     * 消息通知 -> 发送消息到工作通知(场景：所有人)
     * @param agentId   应用的AgentID
     * @param title     消息标题
     * @param text      消息内容
     */
    void workAllSend(String agentId, String title, String text);

    /**
     * 消息通知 -> 发送消息到工作通知(场景：指定人)
     * @param agentId       应用的AgentID
     * @param title         消息标题
     * @param text          消息内容
     * @param userid_list   接收者的userid列表，最大用户列表长度100，例：user123,user456
     */
    void workUserSend(String agentId, String title, String text, String userid_list);


    /**
     * 消息通知 -> 发送消息到工作通知(场景：指定部门)
     * @param agentId       应用的AgentID
     * @param title         消息标题
     * @param text          消息内容
     * @param dept_id_list  接收者的部门id列表，最大列表长度20，例：123,345
     */
    void workDeptSend(String agentId, String title, String text, String dept_id_list);

    // =================== 消息通知Api相关 end ===================

}
