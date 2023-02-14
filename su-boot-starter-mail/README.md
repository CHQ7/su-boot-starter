# su-boot-starter-mail

- `su-boot-starter-mail` 是一个简单易用的邮件发送模块，基于Spring Boot和JavaMail实现，能够快速的构建发送文本邮件、HTML邮件以及带附件的邮件。。


# 安装

- 通过Maven仓库安装，在pom.xml文件中加入以下内容：

```xml
<dependency>
  <groupId>com.yunqi.starter</groupId>
  <artifactId>su-boot-starter-mail</artifactId>
  <version>1.0.0</version>
</dependency>
```

# 使用

- 1.在`application.yml`配置文件中，配置邮件服务相关属性：

```yml
su:
  mail:
    enabled: true
    host-name: 邮件服务器地址
    smtp-port: 邮件服务器端口
    user-name: 邮件发送账号
    password:  邮件发送密码
    from:    邮件发送用户名    
    ssl: true
    charset: UTF-8
```


- 2.使用IMailProvider接口发送邮件，调用其中的sendText()方法发送文本邮件，sendHtml()方法发送HTML邮件：

```
@Autowired
private IMailProvider mailProvider;

// 发送文本邮件
public void sendTextMail(){
    Email email = new Email();
    email.setSubject("Test Text Mail");
    email.setTo("receiver@qq.com");
    email.setContent("This is a test text mail");
    mailProvider.sendText(email);
}

// 发送HTML邮件
public void sendHtmlMail(){
    Email email = new Email();
    email.setSubject("Test HTML Mail");
    email.setTo("receiver@qq.com");
    email.setContent("<h1>This is a test HTML mail</h1>");
    mailProvider.sendHtml(email);
}
```

# 配置说明

| 名称        | 默认值             | 备注 |
|-----------|-----------------| --- |
| enabled   | true            | 是否开启组件 |
| host-name |           | 邮件服务器地址 |
| smtp-port |  | 邮件服务器端口 |
| user-name |             | 邮箱账号 |
| password  |                | 邮箱密码 |
| from      |            | 邮箱用户名 |
| ssl       | true | 邮件服务器是否HTTPS |
| charset   | UTF-8          | 邮箱编码 |
