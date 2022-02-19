package com.yunqi.starter.quartz.service;

import com.yunqi.starter.quartz.entity.QuartzJob;
import org.quartz.*;

/**
 * Quartz管理器,统管Job的增删改查操作
 * Created by @author JsckChin on 2022/2/2
 */
public interface QuartzManager {

    /**
     * 添加一个新Job
     * @param name 任务名称
     * @param group 任务分组
     * @param cron 计划任务表达式
     * @param klass Job类
     */
    void add(String name, String group, String cron, Class<?> klass);


    /**
     * 新增一个任务,如果存在就覆盖
     */
    void add(QuartzJob qj);

    /**
     * 立即执行一个任务,必须任务存在的情况才能执行
     */
    void run(QuartzJob qj);

    /**
     * 立即执行一个任务,必须任务存在的情况才能执行
     */
    void run(JobKey jobKey);

    /**
     * 暂停一个任务
     */
    void pause(QuartzJob qj);

    /**
     * 暂停一个任务
     */
    void pause(JobKey jobKey);

    /**
     * 暂停所有任务
     */
    void pauseAll();

    /**
     * 恢复一个被暂停的任务
     */
    void resume(QuartzJob qj);

    /**
     * 恢复一个被暂停的任务
     */
    void resume(JobKey jobKey);

    /**
     * 恢复所有任务
     */
    void resumeAll();

    /**
     * 触发一个中断, 对于的Job类必须实现InterruptableJob
     */
    void interrupt(QuartzJob qj);

    /**
     * 触发一个中断, 对于的Job类必须实现InterruptableJob
     */
    void interrupt(JobKey jobKey);

    /**
     * 是否存在特定的任务
     */
    boolean exist(QuartzJob qj);

    /**
     * 是否存在特定的任务
     */
    boolean exist(JobKey jobKey);

    /**
     * 清除一个任务
     */
    boolean delete(QuartzJob qj);

    /**
     * 清除一个任务
     */
    boolean delete(JobKey jobKey);

    /**
     * 清除所有的任务
     */
    void clear();

    /**
     * 设置Scheduler
     */
    void setScheduler(Scheduler scheduler);

    void cron(String cron, Class<?> klass);

    void cron(String cron, Class<?> klass, String name, String group);

    QuartzJob fetch(String name, String group);

}
