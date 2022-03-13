package com.yunqi.starter.jdbc.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by @author JsckChin on 2022/2/1
 */
@Data
@ConfigurationProperties(prefix = "su.druid")
public class DruidDataSourceProperties {

    /** 是否开启 */
    boolean enabled = true;

    /**
     * 驱动类名
     * mysql：com.mysql.cj.jdbc.Driver（MySQL 8.0 以上版本）
     * oracle：oracle.jdbc.driver.OracleDriver
     */
    String driverClassName;

    /** 数据库连接地址 */
    private String url;

    /** 数据库用户名 */
    private String username;

    /** 数据库密码 */
    private String password;


    /** 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时 */
    private Integer initialSize = 5;

    /** 最小连接池数量 */
    private Integer minIdle = 10;

    /** 最大连接池数量 */
    private Integer maxActive = 50;

    /**
     * 获取连接时最大等待时间，单位毫秒
     * 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，
     * 如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
     */
    private Integer maxWait = 6000;

    /**
     * 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
     * 有两个含义：
     * 1) Destroy线程会检测连接的间隔时间，如果连接空闲时间大于等于minEvictableIdleTimeMillis则关闭物理连接。
     * 2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
     */
    private Integer timeBetweenEvictionRunsMillis = 60000;

    /** 配置一个连接在池中最小生存的时间，单位是毫秒 */
    private  Integer minEvictableIdleTimeMillis = 300000;

    /** 配置一个连接在池中最大生存的时间，单位是毫秒 */
    private  Integer maxEvictableIdleTimeMillis = 900000;

    /**
     * 用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。
     * 如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
     */
    private String validationQuery = "SELECT 1";

    /**
     * 建议配置为true，不影响性能，并且保证安全性。
     * 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
     */
    private Boolean testWhileIdle = true;

    /** 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。*/
    private Boolean testOnBorrow = false;

    /** 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。*/
    private Boolean testOnReturn = false;

    /** 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。*/
    private Boolean poolPreparedStatements = false;

    /**
     * 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。
     * 在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
     */
    private Integer maxPoolPreparedStatementPerConnectionSize = -1;

    /** 通过connectProperties属性来打开mergeSql功能；慢SQL记录 */
    private String connectionProperties = "druid.stat.mergeSql=true;druid.stat.slowSqlMillis=2000";

    /**
     * 配置监控统计拦截的filters
     * 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：
     * 监控统计用的filter:stat
     * 日志用的filter:log4j
     * 防御sql注入的filter:wall
     */
    private String filters = "stat,wall";


    webStatFilter webStatFilter = new webStatFilter();

    statViewServlet statViewServlet = new statViewServlet();


    @Data
    public static class webStatFilter {

        /** 是否开启 */
        boolean enabled = true;

    }

    @Data
    public static class statViewServlet {

        /** 是否开启 */
        boolean enabled = true;

        private String urlPattern;

        /** IP白名单 (没有配置或者为空，则允许所有访问) 127.0.0.1 只允许本机访问  */
        private String allow = "127.0.0.1";

        /** IP黑名单 (存在共同时，deny优先于allow) */
        private String deny;

        /** 设置控制台登录的用户名 */
        private String loginUsername = "root";

        /** 设置控制台登录的密码 */
        private String loginPassword = "root";

        /** 是否能够重置数据 */
        private String resetEnable = "false";

    }

}
