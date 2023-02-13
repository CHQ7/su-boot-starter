# su-boot-starter

- 组件库

# 组件列表

| 组件名称                         | 组件说明     | 作用域  | 	使用说明                                           | 可靠性 |
|------------------------------|----------|------|-------------------------------------------------|-----|
| su-boot-starter-core         | 核心组件     | 所有项目 | 详见 [README](su-boot-starter-core/README.md)     | 生产  |
| **su-boot-starter-jasypt**   | 安全框架加密组件 | 所有项目 | 详见 [README](su-boot-starter-jasypt/README.md)   | 生产  |
| **su-boot-starter-jdbc**     | 数据库连接组件  | 服务层  | 详见 [README](su-boot-starter-jdbc/README.md)     | 生产  | 
| **su-boot-starter-mail**     | 邮件组件     | 服务层  | 详见 [README](su-boot-starter-mail/README.md)     | 生产  |
| **su-boot-starter-security** | 权限组件     | 控制层  | 详见 [README](su-boot-starter-security/README.md) | 生产  |
| su-boot-starter-database     | 数据库组件    | 服务层  | 详见 [README](su-boot-starter-database/README.md) | 生产  |
| su-boot-starter-log          | 日志组件     | 控制层  | 详见 [README](su-boot-starter-log/README.md)      | 生产  |
| su-boot-starter-web          | web组件    | 所有项目 | 详见 [README](su-boot-starter-web/README.md)      | 生产  |
| su-boot-starter-wx           | 微信小程序组件  | 所有项目 | 详见 [README](su-boot-starter-wx/README.md)       | 生产  |
| su-boot-starter-dingtalk     | 钉钉组件     | 所有项目 | 详见 [README](su-boot-starter-dingtalk/README.md) | 生产  |
| su-boot-starter-quartz       | 任务组件     | 服务层  | 详见 [README](su-boot-starter-quartz/README.md)   | 生产  |
| su-boot-starter-redis        | redis组件  | 所有项目 | 详见 [README](su-boot-starter-redis/README.md)    | 生产  |

## 如何贡献

非常欢迎你的加入！[提一个 Issue](https://github.com/JsckChin/su-boot-starter/issues) 或者提交一个 Pull Request。

**Pull Request:**

1. Fork 代码!
2. 创建自己的分支: `git checkout -b feat/xxxx`
3. 提交你的修改: `git commit -am 'feat(function): add xxxxx'`
4. 推送您的分支: `git push origin feat/xxxx`
5. 提交`pull request`

## Git 贡献提交规范

- 参考 [vue](https://github.com/vuejs/vue/blob/dev/.github/COMMIT_CONVENTION.md) 规范 ([Angular](https://github.com/conventional-changelog/conventional-changelog/tree/master/packages/conventional-changelog-angular))

    - `feat` 增加新功能
    - `fix` 修复问题/BUG
    - `style` 代码风格相关无影响运行结果的
    - `perf` 优化/性能提升
    - `refactor` 重构
    - `revert` 撤销修改
    - `test` 测试相关
    - `docs` 文档/注释
    - `chore` 依赖更新/脚手架配置修改等
    - `workflow` 工作流改进
    - `ci` 持续集成
    - `types` 类型定义文件更改
    - `wip` 开发中
