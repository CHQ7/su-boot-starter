package com.yunqi.starter.jdbc.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Druid 数据源自动配置类
 * Created by @author CHQ on 2022/2/1
 */
@Configuration // 声明该类是一个配置类，可以用于定义 Bean
@ConditionalOnClass(DruidDataSource.class) // 确保 DruidDataSource 类在当前的类路径中可用
@ConditionalOnExpression("${su.druid.enabled:true}")
@AutoConfigureBefore(DataSourceAutoConfiguration.class) // 确保在 DataSourceAutoConfiguration 之前自动配置 Druid 数据源
@EnableConfigurationProperties(DruidProperties.class)
public class DruidDataSourceAutoConfigure {

    @Bean
    @Primary
    @ConditionalOnMissingBean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource(DruidProperties properties) {
        // 创建 DruidDataSource 实例
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(properties.getUrl());
        druidDataSource.setUsername(properties.getUsername());
        druidDataSource.setPassword(properties.getPassword());

        druidDataSource.setDriverClassName(properties.getDriverClassName());
        druidDataSource.setInitialSize(properties.getInitialSize());
        druidDataSource.setMinIdle(properties.getMinIdle());
        druidDataSource.setMaxActive(properties.getMaxActive());
        druidDataSource.setMaxWait(properties.getMaxWait());

        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        druidDataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());

        // 配置一个连接在池中最小生存的时间，单位是毫秒
        druidDataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        druidDataSource.setMaxEvictableIdleTimeMillis(properties.getMaxEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(properties.getValidationQuery());
        druidDataSource.setTestWhileIdle(properties.getTestWhileIdle());
        druidDataSource.setTestOnBorrow(properties.getTestOnBorrow());
        druidDataSource.setTestOnReturn(properties.getTestOnReturn());

        // 打开PSCache，并且指定每个连接上PSCache的大小
        druidDataSource.setPoolPreparedStatements(properties.getPoolPreparedStatements());
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(properties.getMaxPoolPreparedStatementPerConnectionSize());

        // 通过connectProperties属性来打开mergeSql功能；慢SQL记录
        druidDataSource.setConnectionProperties(properties.getConnectionProperties());

        try {
            druidDataSource.setFilters(properties.getFilters());
        } catch (SQLException e) {
            // 采取措施处理异常
            throw new RuntimeException("Error setting filters for DruidDataSource", e);
        }

        return druidDataSource;
    }

    /**
     * 初始化
     */
    @PostConstruct
    public void Initializer() {
        // fix:https://github.com/alibaba/druid/issues/3873
        System.setProperty("druid.mysql.usePingMethod", "false");
    }
}
