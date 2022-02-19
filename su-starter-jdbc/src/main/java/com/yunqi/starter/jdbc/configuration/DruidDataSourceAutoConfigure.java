package com.yunqi.starter.jdbc.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by @author JsckChin on 2022/2/1
 */
@Configuration
@ConditionalOnClass(DruidDataSource.class)
@ConditionalOnExpression("${su.druid.enabled:true}")
@AutoConfigureBefore({com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure.class}) // 在DruidDataSourceAutoConfigure前注入
@EnableConfigurationProperties(DruidDataSourceProperties.class)
public class DruidDataSourceAutoConfigure {

    @Bean
    @Primary
    @ConditionalOnMissingBean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource(DruidDataSourceProperties properties) {
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
            e.printStackTrace();
        }

        return druidDataSource;
    }
}
