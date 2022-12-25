package com.yunqi.starter.dingtalk.provider;

import com.yunqi.starter.common.lang.util.NutMap;

/**
 * OA审批
 *
 */
public interface IDingtalkOaApi {

    /**
     * 创建OA审批实例
     *
     * @param data
     * @return
     */
    String workflowForms(NutMap data);

     /**
     * 获取审批实例信息
     *
     * @param processInstanceId
     * @return
     */
    NutMap workflowProcesses(String processInstanceId);

}
