package com.yunqi.starter.quartz.Job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 测试任务类
 * Created by @author JsckChin on 2022/2/2
 */
@Slf4j
public class TestJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("你好！朋友，这是测试任务。");
    }
}
