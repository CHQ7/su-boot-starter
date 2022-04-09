package com.yunqi.starter.dingtalk.provider;

import org.nutz.dao.entity.Record;
import org.nutz.lang.util.NutMap;

import java.util.List;

/**
 * 钉钉接口
 * Created by @author CHQ on 2022/4/1
 */
public interface IDingtalkProvider {

    /**
     * 获取凭证
     * @return 请求数据
     */
    NutMap getTicket();

    // =================== 消息通知Api相关 begin ===================

    /**
     * 消息通知 -> 发送会话消息
     * <br>
     * <p>注意：发送普通消息是指员工个人在使用应用时，可以通过界面操作的方式向群或其他人的会话中推送消息，例如发送日志的场景。</p>
     * @param sender    消息发送者的userid
     * @param cid       群会话或者个人会话的id
     * @param title     消息标题
     * @param text      消息内容
     */
    void conversationSend(String sender, String cid, String title,String text);

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

    // =================== 部门Api相关 begin ===================

    /**
     * 部门 -> 获取部门列表
     * <br>
     * <p> 本接口只支持获取当前部门的下一级部门基础信息，不支持获取当前部门下所有层级子部门 </p>
     * @return          部门列表
     */
    List<Record> deptList();

    /**
     * 部门 -> 获取部门列表
     * <br>
     * <p> 本接口只支持获取当前部门的下一级部门基础信息，不支持获取当前部门下所有层级子部门 </p>
     * @param deptId    父部门ID(可以为空)
     * @return          部门列表
     */
    List<Record> deptList(String deptId);

    /**
     * 部门 -> 获取部门详情
     * <br>
     * <p> 调用本接口根据部门ID获取指定部门详情 </p>
     * @param deptId    部门ID
     * @return          部门详情
     */
    NutMap deptByFetch(String deptId);

    /**
     * 部门 -> 获取子部门ID列表
     * <br>
     * <p> 调用本接口获取企业部门下的所有直属子部门列表 </p>
     * @param deptId    部门ID
     * @return          子部门ID列表
     */
    List<String> deptSubIds(String deptId);

    /**
     * 部门 -> 获取指定部门的所有父部门列表
     * <br>
     * <p> 调用本接口获取指定部门的所有父部门ID列表 </p>
     * @param deptId    部门ID
     * @return          父部门ID列表
     */
    List<String> deptParentIds(String deptId);

    /**
     * 部门 -> 获取指定用户的所有父部门列表
     * <br>
     * <p> 调用本接口查询指定用户所属的所有父级部门 </p>
     * @param userid    用户ID
     * @return          父部门ID列表
     */
    List<String> deptUserParentIds(String userid);

    // =================== 部门Api相关 end ===================

}
