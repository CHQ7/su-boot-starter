package com.yunqi.starter.dingtalk.provider;

import com.yunqi.starter.common.lang.util.NutMap;
import org.nutz.dao.entity.Record;

import java.util.List;

/**
 * 钉钉部门接口
 * Created by @author CHQ on 2022/11/16
 */
public interface IDingtalkDepartmentApi {

    /**
     * 获取部门列表
     * <br>
     * <p> 本接口只支持获取当前部门的下一级部门基础信息，不支持获取当前部门下所有层级子部门 </p>
     * @return          部门列表
     */
    List<Record> list();

    /**
     * 获取部门列表
     * <br>
     * <p> 本接口只支持获取当前部门的下一级部门基础信息，不支持获取当前部门下所有层级子部门 </p>
     * @param deptId    父部门ID(可以为空)
     * @return          部门列表
     */
    List<Record> list(String deptId);

    /**
     * 获取部门详情
     * <br>
     * <p> 调用本接口根据部门ID获取指定部门详情 </p>
     * @param deptId    部门ID
     * @return          部门详情
     */
    NutMap fetch(String deptId);

    /**
     * 获取子部门ID列表
     * <br>
     * <p> 调用本接口获取企业部门下的所有直属子部门列表 </p>
     * @param deptId    部门ID
     * @return          子部门ID列表
     */
    List<String> subIds(String deptId);

    /**
     * 获取指定部门的所有父部门列表
     * <br>
     * <p> 调用本接口获取指定部门的所有父部门ID列表 </p>
     * @param deptId    部门ID
     * @return          父部门ID列表
     */
    List<String> parentIds(String deptId);

    /**
     * 获取指定用户的所有父部门列表
     * <br>
     * <p> 调用本接口查询指定用户所属的所有父级部门 </p>
     * @param userid    用户ID
     * @return          父部门ID列表
     */
    List<String> userParentIds(String userid);
}
