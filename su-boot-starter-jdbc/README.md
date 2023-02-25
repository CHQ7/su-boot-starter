# su-boot-starter-jdbc

- `su-boot-starter-jdbc` 是一个基于 Spring Boot 的 Druid 动态数据源配置组件。该组件可以帮助您快速启用 Druid 动态数据源，并可以通过配置文件设置相关参数。

# 安装
- 通过Maven仓库安装，在pom.xml文件中加入以下内容：

```xml
<dependency>
  <groupId>com.yunqi.starter</groupId>
  <artifactId>su-boot-starter-jdbc</artifactId>
  <version>1.0.0</version>
</dependency>
```

# 使用

- 使用 `su-boot-starter-jdbc` 非常简单，只需要在配置文件中配置数据库信息即可：

```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/test
    username: root
    password: 123456
```

- 然后就可以通过以下代码获取数据源：

```
@Autowired
private DataSource dataSource;
```

- 最后，使用 jdbcTemplate 进行数据库操作：

```
JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from user");
```

#  Druid 监控

- 启动您的应用程序，并通过浏览器访问 http://localhost:8080/druid/ 即可查看 Druid 监控页面


# 配置说明

| 名称                                        | 默认值                                                    | 备注                                                                            |
|-------------------------------------------|--------------------------------------------------------|-------------------------------------------------------------------------------|
| enabled                                   | true                                                   | 是否开启组件                                                                        |
| initialSize                               | 5                                                      | 初始化时建立物理连接的个数                                                                 |
| minIdle                                   | 10                                                     | 最小连接池数量                                                                       |
| maxActive                                 | 50                                                     | 最大连接池数量                                                                       |
| maxWait                                   | 6000                                                   | 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降                               |
| timeBetweenEvictionRunsMillis             | 60000                                                  | 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒                                               |
| minEvictableIdleTimeMillis                | 300000                                                 | 配置一个连接在池中最小生存的时间，单位是毫秒                                                        |
| maxEvictableIdleTimeMillis                | 900000                                                 | 配置一个连接在池中最大生存的时间，单位是毫秒                                                        |
| validationQuery                           | SELECT 1                                               | 用来检测连接是否有效的sql                                                                |
| testWhileIdle                             | true                                                   | 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效     |
| testOnBorrow                              | false                                                  | 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能                                    |
| testOnReturn                              | false                                                  | 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能                                    |
| poolPreparedStatements                    | false                                                  | 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭 |
| maxPoolPreparedStatementPerConnectionSize | -1                                                     | 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true                    |
| connectionProperties                      | druid.stat.mergeSql=true;druid.stat.slowSqlMillis=2000 | 通过connectProperties属性来打开mergeSql功能；慢SQL记录                                     |
| webStatFilter.enabled                     | true                                                   | 是否开启 Druid Web 网络统计及健康                                                        |
| statViewServlet.urlPattern                | /druid/*                                               | Druid 的管理界面的访问路径                                                              |
| statViewServlet.allow                     | 127.0.0.1                                              | IP白名单 (没有配置或者为空，则允许所有访问) 127.0.0.1 只允许本机访问                                    |
| statViewServlet.deny                      |                                                        | IP黑名单 (存在共同时，deny优先于allow)                                                    |
| statViewServlet.loginUsername             | root                                                   | 设置控制台登录的用户名                                                                   |
| statViewServlet.loginPassword             | root                                                   | 设置控制台登录的密码                                                                    |
| statViewServlet.resetEnable               | false                                                  | 是否能够重置数据                                                                      |
