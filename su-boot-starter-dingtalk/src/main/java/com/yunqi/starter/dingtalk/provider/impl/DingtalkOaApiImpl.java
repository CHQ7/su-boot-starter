package com.yunqi.starter.dingtalk.provider.impl;

import com.yunqi.starter.common.lang.Lang;
import com.yunqi.starter.common.lang.util.NutMap;
import com.yunqi.starter.dingtalk.provider.IDingtalkOaApi;
import com.yunqi.starter.dingtalk.util.DingtalkUtil;


public class DingtalkOaApiImpl implements IDingtalkOaApi {


    @Override
    public String workflowForms(NutMap data) {
        NutMap res = DingtalkUtil.post2("/v1.0/workflow/processInstances" + DingtalkUtil.buildToken(), data);
        return res.getString("instanceId");
    }

    @Override
    public NutMap workflowProcesses(String processInstanceId) {
        NutMap res = DingtalkUtil.get2("/v1.0/workflow/processInstances?processInstanceId="+processInstanceId);
        return Lang.map(res.getString("result"));
    }

    @Override
    public Boolean terminateProcesse(NutMap data) {
        NutMap res = DingtalkUtil.post2("/v1.0/workflow/processInstances/terminate" + DingtalkUtil.buildToken(), data);
        return res.getBoolean("result");
    }

    @Override
    public String insertYiDaForm(NutMap data){
        NutMap res = DingtalkUtil.post2("/v1.0/yida/forms/instances" + DingtalkUtil.buildToken(), data);
        return res.getString("result");
    }
}
