package com.yunqi.starter.database.configuration;

import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.resource.Scans;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * Created by @author JsckChin on 2022/2/1
 */
@Configuration
@ConditionalOnBean({Dao.class})
@ConditionalOnExpression("${su.database.enabled:true}")
@EnableConfigurationProperties(DataBaseProperties.class)
@AutoConfigureAfter({DataBaseAutoConfiguration.class})
public class DatabaseInitializer {

    @Resource
    private Dao dao;

    @Resource
    private SpringResourceLoaction springResourceLoaction;

    @Resource
    private DataBaseProperties properties;

    /**
     * 初始化Nutz
     */
    @PostConstruct
    public void create() {
        Daos.CHECK_COLUMN_NAME_KEYWORD = properties.getGlobal().isCheckColumnNameKeyword();
        Daos.FORCE_HUMP_COLUMN_NAME = properties.getGlobal().isForceHumpColumnName();
        Daos.FORCE_UPPER_COLUMN_NAME = properties.getGlobal().isForceUpperColumnName();
        Daos.FORCE_WRAP_COLUMN_NAME = properties.getGlobal().isForceWrapColumnName();
        Daos.DEFAULT_VARCHAR_WIDTH = properties.getGlobal().getDefaultVarcharWidth();
        boolean create = properties.getRuntime().isCreate();
        boolean migration = properties.getRuntime().isMigration();
        Arrays.stream(properties.getRuntime().getBasepackage()).forEach(pkg -> {
            if (create) {
                Daos.createTablesInPackage(dao, pkg, properties.getRuntime().isFoceCreate());
            }
            if (migration) {
                Daos.migration(dao, pkg, properties.getRuntime().isAddColumn(),
                        properties.getRuntime().isDeleteColumn(), properties.getRuntime().isCheckIndex());
            }
        });

        // 扫描资源
        Scans.me().addResourceLocation(springResourceLoaction);
    }
}
