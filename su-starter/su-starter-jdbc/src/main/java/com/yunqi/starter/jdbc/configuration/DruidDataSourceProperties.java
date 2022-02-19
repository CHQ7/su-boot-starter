package com.yunqi.starter.jdbc.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by @author JsckChin on 2022/2/1
 */
@Data
@ConfigurationProperties(prefix = "su.druid")
public class DruidDataSourceProperties {

    boolean enabled = true;

    /**
     * 驱动类名
     * mysql：com.mysql.cj.jdbc.Driver（MySQL 8.0 以上版本）
     * oracle：oracle.jdbc.driver.OracleDriver
     */
    String driverClassName;

    /**
     * 数据库连接地址
     */
    String url;

    /**
     * 数据库用户名
     */
    String username;

    /**
     * 数据库密码
     */
    String password;


    /**
     * 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
     */
    Integer initialSize = 5;

    /**
     * 最小连接池数量
     */
    Integer minIdle = 10;

    /**
     * 最大连接池数量
     */
    Integer maxActive = 50;

    /**
     * 获取连接时最大等待时间，单位毫秒
     * 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，
     * 如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
     */
    Integer maxWait = 6000;

    /**
     * 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
     * 有两个含义：
     * 1) Destroy线程会检测连接的间隔时间，如果连接空闲时间大于等于minEvictableIdleTimeMillis则关闭物理连接。
     * 2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
     */
    Integer timeBetweenEvictionRunsMillis = 60000;

    /**
     * 配置一个连接在池中最小生存的时间，单位是毫秒
     */
    Integer minEvictableIdleTimeMillis = 300000;

    /**
     * 用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。
     * 如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
     */
    String validationQuery = "select 'x'";

    /**
     * 建议配置为true，不影响性能，并且保证安全性。
     * 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
     */
    Boolean testWhileIdle = true;

    /**
     * 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
     */
    Boolean testOnBorrow = false;

    /**
     * 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
     */
    Boolean testOnReturn = false;

    /**
     * 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
     */
    Boolean poolPreparedStatements = false;

    /**
     * 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。
     * 在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
     */
    Integer maxPoolPreparedStatementPerConnectionSize = -1;

    /**
     * 通过connectProperties属性来打开mergeSql功能；慢SQL记录
     */
    String connectionProperties = "druid.stat.mergeSql=true;druid.stat.slowSqlMillis=2000";

    /**
     * 配置监控统计拦截的filters
     * 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：
     * 监控统计用的filter:stat
     * 日志用的filter:log4j
     * 防御sql注入的filter:wall
     */
    String filters = "stat,wall";

}
