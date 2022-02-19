package com.yunqi.starter.quartz.configuration;

import com.yunqi.starter.database.configuration.DataBaseAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.sql.Sql;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 初始化
 * Created by @author JsckChin on 2022/2/2
 */
@Slf4j
@Configuration
@ConditionalOnClass(Dao.class)
@ConditionalOnExpression("${su.quartz.enabled:true}")
@AutoConfigureAfter(DataBaseAutoConfiguration.class)
@Import({DataBaseAutoConfiguration.class})
public class QuartzInitializer {

    @Resource
    private Dao dao;

    @PostConstruct
    public void init() {
        if (!dao.exists("ims_sys_qrtz_triggers")) {
            log.info("执行Quartz SQL脚本");
            //执行Quartz SQL脚本
            String dbType = dao.getJdbcExpert().getDatabaseType();
            log.debug("dbType:::" + dbType);
            FileSqlManager fmq = new FileSqlManager("quartz/" + dbType.toLowerCase() + ".sql");
            List<Sql> sqlListq = fmq.createCombo(fmq.keys());
            Sql[] sqlsq = sqlListq.toArray(new Sql[sqlListq.size()]);
            for (Sql sql : sqlsq) {
                dao.execute(sql);
            }
        }
    }

}
