# su-boot-starter-mail

- `su-boot-starter-mail` su-boot-starter-mail 是一个快速搭建邮件发送功能的工具包，其使用简单、高效，符合spring-boot风格。

# 特性

- 基于spring-boot邮件发送
- 支持文本邮件发送，HTML邮件发送，支持附件
- 支持邮件服务配置，如：主机地址、端口、用户名、密码、是否开启ssl等


# 安装
## 通过Maven仓库安装，在pom.xml文件中加入以下内容：

```xml
<dependency>
  <groupId>com.yunqi</groupId>
  <artifactId>su-boot-starter-mail</artifactId>
  <version>1.0.0</version>
</dependency>
```

# 使用

- 1.在`application.yml`配置文件中，配置邮件服务相关属性：


| 名称 | 默认值              | 备注 |
| --- |------------------| --- |
| enabled | true             | 是否开启组件 |
| password | jasypt           | 加密密钥 |
| algorithm | PBEWITHHMACSHA512ANDAES_256 | 加密算法 |
| keyObtentionIterations | 1000             | 密钥获取次数 |
| poolSize | 1                | 密钥池大小 |
| providerName | SunJCE                | 加密提供者名称 |
| saltGeneratorClassName | org.jasypt.salt.RandomSaltGenerator              | Salt生成器类名 |
| ivGeneratorClassName | org.jasypt.iv.RandomIvGenerator           | IV生成器类名 |
| stringOutputType | base64           | 加密字符串输出类型 |

- 下面是`application.yml`

```yml
su:
  jasypt:
    enabled: true
    password: jasypt
    algorithm: PBEWITHHMACSHA512ANDAES_256
    key-obtention-iterations: 1000
    pool-size: 1
    provider-name: SunJCE
    salt-generator-class-name: org.jasypt.salt.RandomSaltGenerator
    iv-generator-class-name: org.jasypt.iv.RandomIvGenerator
    string-output-type: base64
```


- 2.在代码中调用IMailProvider接口，调用其中的sendText()方法发送文本邮件，sendHtml()方法发送HTML邮件：

- 在需要使用jasypt的地方，可以通过如下代码调用：

```
@Autowired
private IMailProvider mailProvider;

Email email = new Email();
email.setTo("test@qq.com");
email.setSubject("邮件主题");
email.setContent("邮件内容");
mailProvider.sendText(email);
```



| 名称        | 默认值             | 备注 |
|-----------|-----------------| --- |
| enabled   | true            | 是否开启组件 |
| host-name |           |  |
| smtp-port |  |  |
| user-name |             |  |
| password  |                |  |
| ssl       |           |  |
| from      |            | |
| charset   | UTF-8          |  |
