package com.yunqi.starter.database.configuration;

import com.yunqi.starter.jdbc.configuration.DruidDataSourceAutoConfigure;
import org.nutz.dao.Dao;
import org.nutz.dao.DaoInterceptor;
import org.nutz.dao.impl.DaoRunner;
import org.nutz.dao.impl.NutDao;
import org.nutz.filepool.FilePool;
import org.nutz.filepool.NutFilePool;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Map;


/**
 * Created by @author JsckChin on 2022/1/29
 */
@Configuration
@ConditionalOnClass({Dao.class})
@ConditionalOnExpression("${su.database.enabled:true}")
@AutoConfigureAfter({DruidDataSourceAutoConfigure.class})
@EnableConfigurationProperties(DataBaseProperties.class)
@Import({DruidDataSourceAutoConfigure.class})
public class DataBaseAutoConfiguration {

    /**
     * 默认数据源
     * @param dataSource 数据源
     * @param daoRunner  事务
     * @param context    应用程序上下文
     * @param properties 配置信息
     * @return     dao
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean
    public Dao dao(DataSource dataSource, SpringDaoRunner daoRunner, ApplicationContext context,
                   DataBaseProperties properties){
        // 初始化Nutz
        NutDao dao = new NutDao(dataSource);
        dao.setRunner(daoRunner);

        // Dao操作拦截器
        Map<String, DaoInterceptor> interceptors = context.getBeansOfType(DaoInterceptor.class);
        interceptors.values().forEach(dao::addInterceptor);
        if (properties.getInterceptor().isTime()) {
            dao.addInterceptor("time");
        }
        return dao;
    }

    /**
     * Nutz事务
     * @return Nutz事务
     */
    @Bean
    @ConditionalOnMissingBean(DaoRunner.class)
    public SpringDaoRunner daoRunner() {
        return new SpringDaoRunner();
    }

    /**
     * Nutz资源定位
     * @return 扫描资源路径
     */
    @Bean
    @ConditionalOnMissingBean
    public SpringResourceLoaction springResourceLoaction() {
        return new SpringResourceLoaction();
    }

    /**
     * Nutz文件池
     * @return 文件池
     */
    @Bean
    @ConditionalOnMissingBean
    public FilePool filePool() {
        // 使用相对路径 临时文件最大个数为 1000 个
        return new NutFilePool(".temp", 0);
    }



}
