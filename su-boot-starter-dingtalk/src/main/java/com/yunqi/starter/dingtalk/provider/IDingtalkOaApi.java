package com.yunqi.starter.dingtalk.provider;

import com.yunqi.starter.common.lang.util.NutMap;

/**
 * OA审批
 *
 */
public interface IDingtalkOaApi {

    // ===================  发起OA审批 ===================
    String workflowForms(NutMap data);

    // =================== 获取表单流程节点信息 ===================
    NutMap workflowProcesses(String processInstanceId);

}
