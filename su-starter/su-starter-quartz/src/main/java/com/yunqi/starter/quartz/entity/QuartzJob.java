package com.yunqi.starter.quartz.entity;

import com.yunqi.starter.common.lang.Strings;
import com.yunqi.starter.quartz.utils.Quartzs;
import lombok.Data;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.util.NutMap;
import org.quartz.*;

import java.io.Serializable;

/**
 * Created by @author JsckChin on 2022/2/2
 */
@Data
public class QuartzJob implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String jobName;
    protected String jobGroup;
    protected String cron;
    protected String scheduled;
    protected String className;
    protected String dataMap;
    protected String comment;

    public QuartzJob() {}

    public QuartzJob(JobKey jobKey, Trigger trigger, JobDetail jobDetail) {
        setJobKey(jobKey);
        setTrigger(trigger);
        className = jobDetail.getJobClass().getName();
        dataMap = Json.toJson(jobDetail.getJobDataMap(), JsonFormat.compact());
    }

    public void setJobKey(JobKey jobKey) {
        setJobName(jobKey.getName());
        setJobGroup(jobKey.getGroup());
    }

    public JobKey getJobKey() {
        return JobKey.jobKey(jobName, jobGroup);
    }

    public Trigger getTrigger() {
        if (Strings.isBlank(cron)) {
            NutMap map = Json.fromJson(NutMap.class, scheduled);
            return Quartzs.makeSimpleTrigger(jobName, jobGroup, map.getInt("rate"), map.getInt("count"), map.getLong("delay") ,map.getTime("startTime"),map.getTime("endTime"));
        } else {
            return Quartzs.makeCronTrigger(jobName, jobGroup, cron);
        }
    }

    public void setTrigger(Trigger trigger) {
        if (trigger instanceof CronTrigger) {
            cron = ((CronTrigger)trigger).getCronExpression();
        } else if (trigger instanceof SimpleTrigger) {
            // TODO 怎么玩
            NutMap tmp = new NutMap();
            SimpleTrigger st = (SimpleTrigger)trigger;
            tmp.put("rate", st.getRepeatInterval());
            tmp.put("count", st.getRepeatCount());
            tmp.put("startTime",st.getStartTime());
            tmp.put("endTime",st.getEndTime());
            scheduled = Json.toJson(tmp, JsonFormat.compact());
        }
    }

    public TriggerKey getTriggerKey() {
        return new TriggerKey(jobName, jobGroup);
    }

}
