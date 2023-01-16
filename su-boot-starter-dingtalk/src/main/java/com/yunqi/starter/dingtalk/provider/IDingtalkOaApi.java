package com.yunqi.starter.dingtalk.provider;

import com.yunqi.starter.common.lang.util.NutMap;

/**
 * OA审批
 *
 */
public interface IDingtalkOaApi {

    /**
     * 发起OA审批实例
     * @param data  请求参数
     * @return      审批实例ID
     * @see <a href="https://open.dingtalk.com/document/orgapp-server/create-an-approval-instance">文档网址</a>
     */
    String workflowForms(NutMap data);

     /**
     * 获取单个审批实例详情
     *
     * @param processInstanceId 审批实例ID
     * @return                  返回结果
     * @see <a href="https://open.dingtalk.com/document/orgapp-server/obtains-the-details-of-a-single-approval-instance-pop">文档网址</a>
     */
    NutMap workflowProcesses(String processInstanceId);

    /**
     * 撤销审批实例
     *
     * @param data  请求参数
     * @return      是否撤销成功; true：成功 false：失败
     * @see <a href="https://open.dingtalk.com/document/orgapp-server/revoke-an-approval-instance">文档网址</a>
     */
    Boolean terminateProcesse(NutMap data);

}
