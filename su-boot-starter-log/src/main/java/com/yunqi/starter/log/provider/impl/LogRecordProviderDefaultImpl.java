package com.yunqi.starter.log.provider.impl;

import com.yunqi.starter.common.json.Json;
import com.yunqi.starter.log.model.SLogRecord;
import com.yunqi.starter.log.provider.ILogRecordProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

/**
 * 接收操作日志，可根据情况存储到数据库或发送到MQ
 * Created by @author CHQ on 2022/5/6
 */
@Slf4j
public class LogRecordProviderDefaultImpl implements ILogRecordProvider {

    @Async
    @Override
    public void record(SLogRecord sLog) {
        log.info("【SLog】 log={}", Json.toJson(sLog));
    }
}
