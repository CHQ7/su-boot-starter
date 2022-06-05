package com.yunqi.starter.log.provider;

import com.yunqi.starter.log.model.SysLog;

/**
 * 持久层接口
 * Created by @author CHQ on 2022/2/18
 */
public  interface ISysLogProvider {

    /**
     * 保存日志
     * @param sysLog 日志对象
     */
    void saveLog(SysLog sysLog);
}
