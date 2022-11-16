package com.yunqi.starter.dingtalk.provider;

import com.yunqi.starter.common.lang.util.NutMap;

import java.util.List;

/**
 * 钉钉用户接口
 * Created by @author CHQ on 2022/11/16
 */
public interface IDingtalkUserApi {

    /**
     * 根据手机号查询用户
     * <br>
     * <p> 调用本接口根据手机号获取用户的userId </p>
     * @param mobile    手机号
     * @return          userId
     */
    String fetchByMobile(String mobile);

    /**
     * 查询用户详情
     * <br>
     * <p> 调用本接口获取指定用户的详细信息 </p>
     * @param userId    用户ID
     * @return          用户详情
     */
    NutMap fetch(String userId);

    /**
     * 根据unionid获取用户userid
     * <br>
     * <p> 调用本接口根据unionid获取用户的userid </p>
     * @param unionid    用户ID
     * @return          用户详情
     */
    NutMap fetchByUnionid(String unionid);

    /**
     * 获取部门用户userid列表
     * @param deptId    部门ID
     * @return          用户ID列表
     */
    List<String> listByIds(String deptId);

    /**
     * 获取部门用户基础信息
     * @param deptId    部门ID
     * @param page      分页查询的游标，最开始传0，后续传返回参数中的next_cursor值
     * @param pageSize  分页长度，最大值100
     * @return          简单用户信息
     */
    NutMap deptSimpleList(String deptId, String page, String pageSize);

    /**
     * 获取部门用户基础信息
     * @param deptId    部门ID
     * @param page      分页查询的游标，最开始传0，后续传返回参数中的next_cursor值
     * @param pageSize  分页长度，最大值100
     * @param sort      部门成员的排序规则：entry_asc：代表按照进入部门的时间升序。entry_desc：代表按照进入部门的时间降序。modify_asc：代表按照部门信息修改时间升序。modify_desc：代表按照部门信息修改时间降序。custom：代表用户定义(未定义时按照拼音)排序。默认值：custom。
     * @return          简单用户信息
     */
    NutMap deptSimpleList(String deptId, String page, String pageSize,String sort);

    /**
     * 获取部门用户详情
     * @param deptId    部门ID
     * @param page      分页查询的游标，最开始传0，后续传返回参数中的next_cursor值
     * @param pageSize  分页大小
     * @return          用户信息
     */
    NutMap deptList(String deptId, String page, String pageSize);

    /**
     * 获取部门用户详情
     * @param deptId    部门ID
     * @param page      分页查询的游标，最开始传0，后续传返回参数中的next_cursor值
     * @param pageSize  分页大小
     * @param sort      部门成员的排序规则：entry_asc：代表按照进入部门的时间升序。entry_desc：代表按照进入部门的时间降序。modify_asc：代表按照部门信息修改时间升序。modify_desc：代表按照部门信息修改时间降序。custom：代表用户定义(未定义时按照拼音)排序。默认值：custom。
     * @return          用户信息
     */
    NutMap deptList(String deptId, String page, String pageSize,String sort);


}
