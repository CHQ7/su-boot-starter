package com.yunqi.starter.quartz.service.Impl;

import com.yunqi.starter.common.lang.Lang;
import com.yunqi.starter.common.lang.Strings;
import com.yunqi.starter.quartz.entity.QuartzJob;
import com.yunqi.starter.quartz.service.QuartzManager;
import com.yunqi.starter.quartz.utils.Quartzs;
import org.nutz.json.Json;
import org.nutz.lang.util.NutMap;
import org.quartz.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by @author JsckChin on 2022/2/2
 */
public class QuartzManagerImpl implements QuartzManager {

    /**
     * 通过注入得到
     */
    protected Scheduler scheduler;

    public QuartzManagerImpl(Scheduler scheduler) {
        this.scheduler = scheduler;
    }


    @Override
    public void add(String name, String group, String cron, Class<?> klass) {
        cron(cron, klass, name, group);
    }

    @Override
    public void add(QuartzJob qj) {
        try {
            Class<?> klass = Class.forName(qj.getClassName());
            JobKey jobKey = qj.getJobKey();
            Trigger trigger = qj.getTrigger();
            Set<Trigger> triggers = new HashSet<>();
            triggers.add(trigger);
            NutMap tmp = null;
            if (!Strings.isBlank(qj.getDataMap()))
                tmp = Json.fromJson(NutMap.class, qj.getDataMap());
            JobDataMap data = tmp == null ? new JobDataMap() : new JobDataMap(tmp);
            scheduler.scheduleJob(Quartzs.makeJob(jobKey, klass, data), triggers, true);
        }
        catch (ClassNotFoundException e) {
            throw Lang.wrapThrow(e);
        }
        catch (SchedulerException e) {
            throw Lang.wrapThrow(e);
        }
    }

    @Override
    public void run(QuartzJob qj) {
        run(qj.getJobKey());
    }

    @Override
    public void run(JobKey jobKey) {
        try {
            scheduler.triggerJob(jobKey);
        }
        catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void pause(QuartzJob qj) {
        pause(qj.getJobKey());
    }

    @Override
    public void pause(JobKey jobKey) {
        try {
            scheduler.pauseJob(jobKey);
        }
        catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void pauseAll() {
        try {
            scheduler.pauseAll();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void resume(QuartzJob qj) {
        resume(qj.getJobKey());
    }

    @Override
    public void resume(JobKey jobKey) {
        try {
            scheduler.resumeJob(jobKey);
        }
        catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void resumeAll() {
        try {
            scheduler.resumeAll();
        }
        catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void interrupt(QuartzJob qj) {
        interrupt(qj.getJobKey());
    }

    @Override
    public void interrupt(JobKey jobKey) {
        try {
            scheduler.interrupt(jobKey);
        }
        catch (UnableToInterruptJobException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exist(QuartzJob qj) {
        return exist(qj.getJobKey());
    }

    @Override
    public boolean exist(JobKey jobKey) {
        try {
            return scheduler.checkExists(jobKey);
        }
        catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(QuartzJob qj) {
        return delete(qj.getJobKey());
    }

    @Override
    public boolean delete(JobKey jobKey) {
        try {
            // 如果存在这个任务，则删除
            if(scheduler.checkExists(jobKey)){
                return scheduler.deleteJob(jobKey);
            }
            return false;
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        try {
            scheduler.clear();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void cron(String cron, Class<?> klass) {
        String name = klass.getName();
        this.cron(cron, klass, name, Scheduler.DEFAULT_GROUP);
    }

    @Override
    public void cron(String cron, Class<?> klass, String name, String group) {
        QuartzJob qj = new QuartzJob();
        qj.setClassName(klass.getName());
        qj.setJobName(name);
        qj.setJobGroup(group);
        qj.setCron(cron);
        add(qj);
    }

    @Override
    public QuartzJob fetch(String name, String group) {
        try {
            JobKey jobKey = new JobKey(name, group);
            if (!scheduler.checkExists(jobKey))
                return null;
            JobDetail jd = scheduler.getJobDetail(jobKey);
            Trigger trigger = scheduler.getTrigger(new TriggerKey(name, group));
            return new QuartzJob(jobKey, trigger, jd);
        }
        catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
