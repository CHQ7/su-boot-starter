package com.yunqi.starter.log.provider;

import com.yunqi.starter.log.model.SysLog;

/**
 * Created by @author JsckChin on 2022/2/18
 */
public interface ISysLogProvider {

    /**
     * 保存日志
     *
     * @param sysLog 日志对象
     */
    void saveLog(SysLog sysLog);
}
