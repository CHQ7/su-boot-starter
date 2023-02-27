package com.yunqi.starter.log.provider;

import com.yunqi.starter.log.model.SLogRecord;

/**
 * 持久层接口
 * Created by @author CHQ on 2022/2/18
 */
public  interface ILogRecordProvider {

    /**
     * 保存日志(请异步保存)
     * @param sysLog 日志实体
     */
    void record(SLogRecord sysLog);
}
