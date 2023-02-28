### su-boot-starter-log

`su-boot-starter-log` 是一个基于 Spring Boot 开发的日志组件，旨在简化日志配置和操作，并提供注解优雅地记录项目中的操作日志，对业务代码无侵入。

    你可以方便地将所有日志推送到下列数据管道：
    1.本地处理
    2.发送至RabbitMQ 
    3.发送至RocketMQ
    4.发送至SpringCloud Stream

### 安装

通过Maven仓库安装，在pom.xml文件中加入以下内容：

```xml
<dependency>
  <groupId>com.yunqi.starter</groupId>
  <artifactId>su-boot-starter-log</artifactId>
  <version>1.0.0</version>
</dependency>
```

### 注解鉴权

注解鉴权 —— 优雅的将鉴权与业务代码分离！


```
@SLog(tag = "系统模块-系统参数",  type = LogType.INSERT)
public String test(UserDto userDto) {
    return "test";
}

```


| 字段     | 默认值           | 备注       |
|--------|---------------|----------|
| tag    | true          | 日志标签     |
| type   | LogType.OTHER | 日志类型     |
| value  |               | 日志内容     |
| param  | true          | 是否记录传递参数 |
| result | true          | 是否记录执行结果 |

### 使用

1.你可以零配置启动项目，但同时你也可以在`application.yml`配置文件中，添加配置：

```yml
su:
  log:
    enabled: true
    log: true
```

2.接收操作日志：

> 我们接收到操作日志后，可根据实际情况来选择如何处理，是存储到数据库还是发送到MQ都可以。 实现 ILogRecordProvider 接口，并交给Spring管理。

```
@Slf4j
@Service
public class ISysLogService implements ILogRecordProvider {
    
    @Override
    public void record(SLogRecord sLog) {
        log.info("【SLog】 log={}", Json.toJson(sLog));
    }
}
```

### 配置说明

| 名称      | 默认值   | 备注       |
|---------|-------|----------|
| enabled | true  | 是否开启组件   |
| log     | false | 是否打印操作日志 |


