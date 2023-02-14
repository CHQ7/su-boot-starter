# su-boot-starter-jasypt

- `su-boot-starter-jasypt` 是一个基于 Jasypt 和 Spring Boot 开发的加密解密组件。它能够帮助开发者快速的在 Spring Boot 项目中进行数据加密解密操作。

# 安装
- 通过Maven仓库安装，在pom.xml文件中加入以下内容：

```xml
<dependency>
  <groupId>com.yunqi.starter</groupId>
  <artifactId>su-boot-starter-jasypt</artifactId>
  <version>1.0.0</version>
</dependency>
```

# 使用

- 1.你可以零配置启动项目 ，但同时你也可以在`application.yml`配置文件中，添加 Jasypt 的配置：

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

- 2.在代码中调用，在需要使用jasypt的地方，可以通过如下代码调用：

```
public class JasyptTest {

    @Autowired
    StringEncryptor stringEncryptor;

    @Test
    public void testEncrypt() {
        // 明文
        String message = "Hello World";
        // 加密
        String encryptedMessage = stringEncryptor.encrypt(message);
        // 解密
        String decryptMessage = stringEncryptor.decrypt(encryptedMessage);
        
        // 打印
        log.info(encryptedMessage);
        log.info(decryptMessage);
    }
}
```

# 配置说明

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

# 注意事项

在使用 `su-boot-starter-jasypt` 组件时，需要注意以下几点：

- 配置的加密密钥，要妥善保存。
- 在加密解密过程中，请注意数据的安全性。
- 在使用 `su-boot-starter-jasypt` 组件时，请务必遵循 Jasypt 的相关使用规则。
- 如果需要更改加密解密密钥，请修改配置文件中的密钥并重新启动项目。


