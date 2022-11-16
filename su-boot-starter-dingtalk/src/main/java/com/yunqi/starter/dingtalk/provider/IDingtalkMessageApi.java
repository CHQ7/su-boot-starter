package com.yunqi.starter.dingtalk.provider;

/**
 * 消息通知
 * Created by @author CHQ on 2022/11/16
 */
public interface IDingtalkMessageApi {

    /**
     * 发送会话消息
     * <br>
     * <p>注意：发送普通消息是指员工个人在使用应用时，可以通过界面操作的方式向群或其他人的会话中推送消息，例如发送日志的场景。</p>
     * @param sender    消息发送者的userid
     * @param chatId    群会话或者个人会话的id
     * @param title     消息标题
     * @param text      消息内容
     */
    void conversationSend(String sender, String chatId, String title,String text);

    /**
     * 发送普通消息到企业群
     * @param chatId    企业群会话的ID
     * @param title     消息标题
     * @param text      消息内容
     */
    void chatSend(String chatId, String title,String text);

    /**
     * 发送消息到工作通知(场景：所有人)
     * @param agentId   应用的AgentID
     * @param title     消息标题
     * @param text      消息内容
     */
    void send(String agentId, String title, String text);

    /**
     * 发送消息到工作通知(场景：指定人)
     * @param agentId       应用的AgentID
     * @param title         消息标题
     * @param text          消息内容
     * @param userid_list   接收者的userid列表，最大用户列表长度100，例：user123,user456
     */
    void userSend(String agentId, String title, String text, String userid_list);


    /**
     * 发送消息到工作通知(场景：指定部门)
     * @param agentId       应用的AgentID
     * @param title         消息标题
     * @param text          消息内容
     * @param dept_id_list  接收者的部门id列表，最大列表长度20，例：123,345
     */
    void deptSend(String agentId, String title, String text, String dept_id_list);
}
