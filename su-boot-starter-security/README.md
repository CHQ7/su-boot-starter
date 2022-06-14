# su-boot-starter-security

- 权限组件

## 配置说明

| 名称               | 默认值              | 备注 |
|------------------|------------------| --- |
| enabled          | true             | 是否开启组件 |
| token-name       |     x-token       | token名称 (同时也是cookie名称) |
| timeout          | 60 * 60 * 24 * 30 | token的长久有效期(单位:秒) 默认30天, -1代表永久 |
| activity-timeout | -1             | token临时有效期 [指定时间内无操作就视为token过期] (单位: 秒), 默认-1 代表不限制 |
| log              | false             | 是否打印操作日志 |
| log              | false             | 是否打印操作日志 |
| log              | false             | 是否打印操作日志 |
| log              | false             | 是否打印操作日志 |

# 更新日志

2022-02-16
* 优化:优化注入方式
* 优化:优化依赖作用域

2022-02-17
* 新增:新增权限全局异常处理

