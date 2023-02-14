# 在 SpringBoot 环境集成

- 本篇带你从零开始集成 `su-boot-starter-security`，从而快速熟悉框架的使用姿势。

## 1、创建项目

- 在 IDE 中新建一个 SpringBoot 项目，例如：demo-springboot（不会的同学请自行百度）

## 2、添加依赖

- PS: 注意 `su-boot-starter-security` 版本，请到仓库查看最新版本
```
<dependency>
  <groupId>com.yunqi</groupId>
  <artifactId>su-boot-starter-security</artifactId>
  <version>1.0.0</version>
</dependency>
```

## 3、设置配置文件

- 你可以零配置启动项目 ，但同时你也可以在 `application.yml` 中增加如下配置，定制性使用框架：
```
server:
  # 端口
  port: 8081

su:
  security:
    # token名称 (同时也是cookie名称)
    token-name: token
    # token有效期，单位s 默认30天, -1代表永不过期
    timeout: 2592000
    # token临时有效期 (指定时间内无操作就视为token过期) 单位: 秒
    activity-timeout: -1
    # 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)
    is-concurrent: true
    # 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)
    is-share: true
    # token风格
    token-style: uuid
    # 是否输出操作日志
    is-log: false

```

## 4、创建启动类

- 在项目中新建包 `com.yunqi` ，在此包内新建主类 `DemoApplication.java`，复制以下代码：

```
@SpringBootApplication
public class DemoApplication{
    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(DemoApplication.class, args);
        System.out.println("启动成功：Security配置如下：" + SaManager.getConfig());
    }
}
```

## 5、创建测试Controller

```
@RestController
@RequestMapping("/user/")
public class UserController {

    // 测试登录，浏览器访问： http://localhost:8081/user/doLogin?username=zhang&password=123456
    @RequestMapping("doLogin")
    public String doLogin(String username, String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对 
        if("zhang".equals(username) && "123456".equals(password)) {
            SecurityUtil.login(10001);
            return "登录成功";
        }
        return "登录失败";
    }

    // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
    @RequestMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + SecurityUtil.isLogin();
    }

}
```

## 6、运行

- 启动代码，从浏览器依次访问上述测试接口：
```
// 测试登录，浏览器访问： http://localhost:8081/user/doLogin?username=zhang&password=123456
// -> 登录成功

// 测试登录，浏览器访问： http://localhost:8081/user/isLogin
// -> 当前会话是否登录：true

```

## 详细了解

- 通过这个示例，你已经对 `su-boot-starter-security` 有了初步的了解
