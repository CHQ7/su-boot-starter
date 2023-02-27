package com.yunqi.starter.log.provider.impl;

import com.yunqi.starter.common.json.Json;
import com.yunqi.starter.log.model.SLogRecord;
import com.yunqi.starter.log.provider.ILogRecordProvider;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认实现
 * Created by @author CHQ on 2022/5/6
 */
@Slf4j
public class DefaultLogRecordProviderImpl implements ILogRecordProvider {

    @Override
    public void record(SLogRecord sLog) {
        log.info("【SLog】 log={}", Json.toJson(sLog));
    }
}
