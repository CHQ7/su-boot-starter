package com.yunqi.starter.dingtalk.spi;

import com.yunqi.starter.dingtalk.configuration.DingtalkConfig;
import com.yunqi.starter.dingtalk.provider.IDingtalkProvider;
import com.yunqi.starter.dingtalk.provider.impl.DingtalkProviderImpl;
import com.yunqi.starter.dingtalk.util.DingtalkToken;
import lombok.extern.slf4j.Slf4j;
import org.nutz.dao.entity.Record;
import org.nutz.json.Json;
import org.nutz.lang.util.NutMap;

import java.util.List;

/**
 * 钉钉API工具类
 * Created by @author JsckChin on 2022/4/1
 */
@Slf4j
public class Dingtalks {

    /**
     * 配置文件 Bean
     */
    public volatile static DingtalkConfig config;
    public static void setConfig(DingtalkConfig config) {
        Dingtalks.config = config;
        if(config.getIsLog()){
            log.info("打印钉钉配置如下 ->\n{}", Json.toJson(Dingtalks.config));
        }
    }

    /**
     * 底层的钉钉API对象
     */
    public static IDingtalkProvider iDingtalkProvider = new DingtalkProviderImpl();

    // =================== 获取Api 相关 ===================

    /**
     * 获取凭证
     * @return 钉钉凭证
     */
    public static String getToken(){
        return DingtalkToken.getToken();
    }

    /**
     * 获取凭证
     * @return 请求数据
     */
    public static NutMap getTicket(){
        return iDingtalkProvider.getTicket();
    }

    /**
     * 消息通知 -> 发送普通消息
     * @param sender    消息发送者的userid
     * @param cid       群会话或者个人会话的id
     * @param title     消息标题
     * @param text      消息内容
     */
    public static void conversationSend(String sender, String cid, String title,String text){
        iDingtalkProvider.conversationSend(sender, cid, title, text);
    }

    /**
     * 消息通知 -> 发送消息到企业群
     * @param chatId    企业群会话的ID
     * @param title     消息标题
     * @param text      消息内容
     */
    public static void chatSend(String chatId, String title,String text){
        iDingtalkProvider.chatSend(chatId, title, text);
    }

    /**
     * 消息通知 -> 发送消息到工作通知(场景：所有人)
     * @param agentId   应用的AgentID
     * @param title     消息标题
     * @param text      消息内容
     */
    public static void workAllSend(String agentId, String title, String text){
        iDingtalkProvider.workAllSend(agentId, title, text);
    }

    /**
     * 消息通知 -> 发送消息到工作通知(场景：指定人)
     * @param agentId       应用的AgentID
     * @param title         消息标题
     * @param text          消息内容
     * @param userid_list   接收者的userid列表，最大用户列表长度100，例：user123,user456
     */
    public static void workUserSend(String agentId, String title, String text, String userid_list){
        iDingtalkProvider.workUserSend(agentId, title, text, userid_list);
    }

    /**
     * 消息通知 -> 发送消息到工作通知(场景：指定部门)
     * @param agentId       应用的AgentID
     * @param title         消息标题
     * @param text          消息内容
     * @param dept_id_list  接收者的部门id列表，最大列表长度20，例：123,345
     */
    public static void workDeptSend(String agentId, String title, String text, String dept_id_list){
        iDingtalkProvider.workDeptSend(agentId, title, text, dept_id_list);
    }

    /**
     * 部门 -> 获取部门列表
     * <br>
     * <p> 本接口只支持获取当前部门的下一级部门基础信息，不支持获取当前部门下所有层级子部门 </p>
     * @return          部门列表
     */
    public static List<Record> deptList(){
        return iDingtalkProvider.deptList();
    }

    /**
     * 部门 -> 获取部门列表
     * <br>
     * <p> 本接口只支持获取当前部门的下一级部门基础信息，不支持获取当前部门下所有层级子部门 </p>
     * @param deptId    父部门ID(可以为空)
     * @return          部门列表
     */
    public static List<Record> deptList(String deptId){
        return iDingtalkProvider.deptList(deptId);
    }

    /**
     * 部门 -> 获取部门详情
     * <br>
     * <p> 调用本接口根据部门ID获取指定部门详情 </p>
     * @param deptId    部门ID
     * @return          部门详情
     */
    public static NutMap deptByFetch(String deptId){
        return iDingtalkProvider.deptByFetch(deptId);
    }

    /**
     * 部门 -> 获取子部门ID列表
     * <br>
     * <p> 调用本接口获取企业部门下的所有直属子部门列表 </p>
     * @param deptId    部门ID
     * @return          子部门ID列表
     */
    public static List<String> deptSubIds(String deptId){
        return iDingtalkProvider.deptSubIds(deptId);
    }

    /**
     * 部门 -> 获取指定部门的所有父部门列表
     * <br>
     * <p> 调用本接口获取指定部门的所有父部门ID列表 </p>
     * @param deptId    部门ID
     * @return          父部门ID列表
     */
    public static List<String> deptParentIds(String deptId){
        return iDingtalkProvider.deptParentIds(deptId);
    }


}
