package com.yunqi.starter.quartz.configuration;

import com.yunqi.starter.jdbc.configuration.DruidDataSourceAutoConfigure;
import com.yunqi.starter.quartz.service.Impl.QuartzManagerImpl;
import com.yunqi.starter.quartz.service.QuartzManager;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.spi.JobFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

/**
 * Created by @author JsckChin on 2022/2/2
 */
@Slf4j
@Configuration
@ConditionalOnClass(DataSource.class)
@ConditionalOnExpression("${su.quartz.enabled:true}")
@EnableConfigurationProperties(QuartzProperties.class)
@AutoConfigureAfter(DruidDataSourceAutoConfigure.class)
@Import({DruidDataSourceAutoConfigure.class})
public class QuartzAutoConfiguration {


    /**
     * 配置JobFactory
     * <br>
     * 注入SpringLiquibase以确保liquibase已初始化并已创建quartz表
     * @param applicationContext ApplicationContext
     * @return jobFactory
     */
    @Bean
    @ConditionalOnMissingBean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    /**
     * SchedulerFactoryBean这个类的真正作用提供了对org.quartz.Scheduler的创建与配置，并且会管理它的生命周期与Spring同步。
     *
     * org.quartz.Scheduler: 调度器。所有的调度都是由它控制。
     * @param dataSource    为SchedulerFactory配置数据源
     * @param jobFactory    为SchedulerFactory配置JobFactory
     * @return              SchedulerFactoryBean
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource, JobFactory jobFactory, QuartzProperties properties) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setJobFactory(jobFactory);
        factory.setApplicationContextSchedulerContextKey("applicationContextKey");
        // 可选,QuartzScheduler启动时更新己存在的Job,这样就不用每次修改targetObject后删除qrtz_job_details表对应记录
        factory.setOverwriteExistingJobs(true);
        // 设置自动启动，默认为true
        factory.setAutoStartup(true);

        // #==============================================================
        // # Quartz参数配置
        // #==============================================================
        Properties prop = new Properties();

        // #==============================================================
        // # Configure Main Scheduler Properties
        // #==============================================================
        prop.put("org.quartz.scheduler.instanceName", "QuartzScheduler");
        prop.put("org.quartz.scheduler.instanceId", "AUTO");

        // #==============================================================
        // # Configure JobStore JobStore配置、集群配置 isClustered=true
        // #==============================================================

        // class:job的存储方式,可以选择存储在内存中或者持久化数据库中
        // 1、存储在内存:org.quartz.simpl.RAMJobStore
        // 2、存储在数据库:org.quartz.impl.jdbcjobstore.JobStoreTX
        // tablePrefix:数据表前缀，默认QRTZ_
        // isClustered:是否以集群方式运行
        // clusterCheckinInterval:调度实例失效的检查时间间隔,默认值20000毫秒 (即20秒)
        // misfireThreshold:最大能忍受的触发超时时间,默认值60000毫秒 (即60秒)

        // fix:spring boot 2.6 中不需要设置这个值,datasource自己决定
        // prop.put("org.quartz.jobStore.class", "org.springframework.scheduling.quartz.LocalDataSourceJobStore");
        prop.put("org.quartz.jobStore.tablePrefix", "IMS_SYS_QRTZ_");
        prop.put("org.quartz.jobStore.isClustered", "true");
        prop.put("org.quartz.jobStore.clusterCheckinInterval", "20000");
        prop.put("org.quartz.jobStore.misfireThreshold", "120000");

        // #==============================================================
        // # Configure ThreadPool 线程池配置
        // #==============================================================
        // class:线程池的实现类
        // threadCount:线程池中的线程数量 至少为1（无默认值）(1-100)
        // threadPriority:线程的优先级（最大为10，最小为1，默认为5）
        // threadsInheritContextClassLoaderOfInitializingThread:线程继承初始化线程的上下文类加载器
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "20");
        prop.put("org.quartz.threadPool.threadPriority", "5");
        prop.put("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread", "true");
        // 外部传入的属性优先级更高
        Map<String, String> propertiesMap = properties.getProperties();
        if (!CollectionUtils.isEmpty(propertiesMap)) {
            prop.putAll(propertiesMap);
        }
        factory.setQuartzProperties(prop);
        return factory;
    }


    @Bean
    public TimeZone quartzTimeZone(QuartzProperties properties) {
        return TimeZone.getTimeZone(properties.getTimeZone());
    }


    @Bean
    @ConditionalOnMissingBean
    public QuartzManager quartzManager(Scheduler scheduler) {
        return new QuartzManagerImpl(scheduler);
    }

}
