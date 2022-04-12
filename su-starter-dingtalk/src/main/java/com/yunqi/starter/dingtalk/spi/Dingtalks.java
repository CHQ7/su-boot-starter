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
 * Created by @author CHQ on 2022/4/1
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


    /**
     * 角色 -> 获取角色列表
     * <br>
     * <p> 调用本接口获取角色列表及是否存在下一页 </p>
     * @param page      支持分页查询，与size参数同时设置时才生效，此参数代表偏移量，偏移量从0开始。
     * @param pageSize  支持分页查询，与offset参数同时设置时才生效，此参数代表分页大小，默认值20，最大值200。
     * @return          角色信息
     */
    public static NutMap roleList2(String page, String pageSize){
        return iDingtalkProvider.roleList2(page, pageSize);
    }

    /**
     * 角色 -> 获取角色列表
     * <br>
     * <p> 调用本接口获取角色列表 </p>
     * @return          角色列表
     */
    public static List<Record> roleList(){
        return iDingtalkProvider.roleList();
    }

    /**
     * 角色 -> 获取角色列表
     * <br>
     * <p> 调用本接口获取角色列表 </p>
     * @param page          支持分页查询，与size参数同时设置时才生效，此参数代表偏移量，偏移量从0开始。
     * @param pageSize      支持分页查询，与offset参数同时设置时才生效，此参数代表分页大小，默认值20，最大值200。
     * @return              角色列表
     */
    public static List<Record> roleList(String page, String pageSize){
        return iDingtalkProvider.roleList(page, pageSize);
    }


    /**
     * 角色 -> 获取角色组列表
     * <br>
     * <p> 调用本接口获取角色组信息 </p>
     * @param groupId   角色组的ID
     * @return          角色列表
     */
    public static NutMap roleGroup(String groupId){
        return iDingtalkProvider.roleGroup(groupId);
    }


    /**
     * 角色 -> 获取指定角色的员工列表
     * <br>
     * <p> 调用本接口获取指定角色的员工列表 </p>
     * @param roleId    角色ID
     * @param page      支持分页查询，与size参数同时设置时才生效，此参数代表偏移量，偏移量从0开始。
     * @param pageSize  支持分页查询，与offset参数同时设置时才生效，此参数代表分页大小，默认值20，最大100。
     * @return          用户列表
     */
    public static NutMap roleSimpleUserList(String roleId, String page, String pageSize){
        return iDingtalkProvider.roleSimpleUserList(roleId, page, pageSize);
    }

    /**
     * 角色 -> 获取角色详情
     * <br>
     * <p> 调用本接口根据角色ID获取指定角色详情 </p>
     * @param roleId    角色ID
     * @return          角色详情
     */
    public static NutMap roleByFetch(String roleId){
        return iDingtalkProvider.roleByFetch(roleId);
    }

    /**
     * 用户 -> 根据手机号查询用户
     * <br>
     * <p> 调用本接口根据手机号获取用户的userId </p>
     * @param mobile    手机号
     * @return          userId
     */
    public static String userByMobile(String mobile){
        return iDingtalkProvider.userByMobile(mobile);
    }

    /**
     * 用户 -> 查询用户详情
     * <br>
     * <p> 调用本接口获取指定用户的详细信息 </p>
     * @param userId    用户ID
     * @return          用户详情
     */
    public static NutMap userByFetch(String userId){
        return iDingtalkProvider.userByFetch(userId);
    }

    /**
     * 用户 -> 获取部门用户userid列表
     * @param deptId    部门ID
     * @return          用户ID列表
     */
    public static List<String> userListIds(String deptId){
        return iDingtalkProvider.userListIds(deptId);
    }

    /**
     * 用户 -> 获取部门用户基础信息
     * @param deptId    部门ID
     * @param page      分页查询的游标，最开始传0，后续传返回参数中的next_cursor值
     * @param pageSize  分页长度，最大值100
     * @return          简单用户信息
     */
    public static NutMap userDeptSimpleList(String deptId, String page, String pageSize){
        return iDingtalkProvider.userDeptSimpleList(deptId, page, pageSize);
    }

    /**
     * 用户 -> 获取部门用户基础信息
     * @param deptId    部门ID
     * @param page      分页查询的游标，最开始传0，后续传返回参数中的next_cursor值
     * @param pageSize  分页长度，最大值100
     * @param sort      部门成员的排序规则：entry_asc：代表按照进入部门的时间升序。entry_desc：代表按照进入部门的时间降序。modify_asc：代表按照部门信息修改时间升序。modify_desc：代表按照部门信息修改时间降序。custom：代表用户定义(未定义时按照拼音)排序。默认值：custom。
     * @return          简单用户信息
     */
    public static NutMap userDeptSimpleList(String deptId, String page, String pageSize,String sort){
        return iDingtalkProvider.userDeptSimpleList(deptId, page, pageSize, sort);
    }

    /**
     * 用户 -> 获取部门用户详情
     * @param deptId    部门ID
     * @param page      分页查询的游标，最开始传0，后续传返回参数中的next_cursor值
     * @param pageSize  分页大小
     * @return          用户信息
     */
    public static NutMap userDeptList(String deptId, String page, String pageSize){
        return iDingtalkProvider.userDeptList(deptId, page, pageSize);
    }

    /**
     * 用户 -> 获取部门用户详情
     * @param deptId    部门ID
     * @param page      分页查询的游标，最开始传0，后续传返回参数中的next_cursor值
     * @param pageSize  分页大小
     * @param sort      部门成员的排序规则：entry_asc：代表按照进入部门的时间升序。entry_desc：代表按照进入部门的时间降序。modify_asc：代表按照部门信息修改时间升序。modify_desc：代表按照部门信息修改时间降序。custom：代表用户定义(未定义时按照拼音)排序。默认值：custom。
     * @return          用户信息
     */
    public static NutMap userDeptList(String deptId, String page, String pageSize,String sort){
        return iDingtalkProvider.userDeptList(deptId, page, pageSize, sort);
    }

    /**
     * 钉工牌 - > 解码钉工牌电子码
     * @param payCode   码值，解码接口仅支持钉钉侧生成的码值
     * @return          解码信息
     */
    public static NutMap badgeDecode(String payCode){
        return iDingtalkProvider.badgeDecode(payCode);
    }

}
