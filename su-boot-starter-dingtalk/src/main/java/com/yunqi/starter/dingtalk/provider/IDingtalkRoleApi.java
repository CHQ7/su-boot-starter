package com.yunqi.starter.dingtalk.provider;

import com.yunqi.starter.common.lang.util.NutMap;
import org.nutz.dao.entity.Record;

import java.util.List;

/**
 * 钉钉角色接口
 * Created by @author CHQ on 2022/11/16
 */
public interface IDingtalkRoleApi {

    /**
     * 获取角色列表
     * <br>
     * <p> 调用本接口获取角色列表及是否存在下一页 </p>
     * @param page      支持分页查询，与size参数同时设置时才生效，此参数代表偏移量，偏移量从0开始。
     * @param pageSize  支持分页查询，与offset参数同时设置时才生效，此参数代表分页大小，默认值20，最大值200。
     * @return          角色信息
     */
    NutMap List2(String page, String pageSize);

    /**
     * 获取角色列表
     * <br>
     * <p> 调用本接口获取角色列表 </p>
     * @return          角色列表
     */
    List<Record> list();

    /**
     * 获取角色列表
     * <br>
     * <p> 调用本接口获取角色列表 </p>
     * @param page          支持分页查询，与size参数同时设置时才生效，此参数代表偏移量，偏移量从0开始。
     * @param pageSize      支持分页查询，与offset参数同时设置时才生效，此参数代表分页大小，默认值20，最大值200。
     * @return              角色列表
     */
    List<Record> list(String page, String pageSize);

    /**
     * 获取角色组列表
     * <br>
     * <p> 调用本接口获取角色组信息 </p>
     * @param groupId   角色组的ID
     * @return          角色列表
     */
    NutMap group(String groupId);

    /**
     * 获取角色详情
     * <br>
     * <p> 调用本接口根据角色ID获取指定角色详情 </p>
     * @param roleId    角色ID
     * @return          角色详情
     */
    NutMap fetch(String roleId);

    /**
     * 获取指定角色的员工列表
     * <br>
     * <p> 调用本接口获取指定角色的员工列表 </p>
     * @param roleId    角色ID
     * @param page      支持分页查询，与size参数同时设置时才生效，此参数代表偏移量，偏移量从0开始。
     * @param pageSize  支持分页查询，与offset参数同时设置时才生效，此参数代表分页大小，默认值20，最大100。
     * @return          用户列表
     */
    NutMap simpleUserList(String roleId, String page, String pageSize);


}
