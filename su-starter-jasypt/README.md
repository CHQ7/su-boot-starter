# su-starter-jasypt

Jasypt安全框架加密组件

## 设置配置文件

```yaml
su:
  # 默认配置，可以不加
  jasypt:
    enabled: true
    password: egsnhm
    algorithm: PBEWithMD5AndDES
    key-obtention-iterations: 1000
    pool-size: 1
    salt-generator-class-name: org.jasypt.salt.RandomSaltGenerator
    string-output-type: base64
```

## 配置文件说明

| 名称 | 默认值              | 备注 |
| --- |------------------| --- |
| enabled | true             | 是否开启组件 |
| password | egsnhm           | 加密盐值,默认值:egsnhm 可自定义 |
| algorithm | PBEWithMD5AndDES | 置加密算法的值,默认算法:PBEWithMD5AndDES |
| keyObtentionIterations | 1000             | 设置用于获取加密密钥的散列迭代次数,默认值:1000 |
| poolSize | 1                | 设置要创建的加密器池的大小,默认值:1 |
| saltGeneratorClassName | org.jasypt.salt.RandomSaltGenerator              | 设置盐生成器,默认值:org.jasypt.salt.RandomSaltGenerator |
| stringOutputType | base64           | 置字符串输出将被编码的形式,默认值:base64 |

# 使用方式

# 方式一：通过工具类调用
```
// 待加密字符串
String txt = "abcd";
// 加密
String ciphertext = Jasypts.encrypt(txt);
// 解密
String decrypttext = Jasypts.decrypt(ciphertext);
// 打印
System.out.printf("原文:%s%n密文:%s%n解密:%s%n", txt, ciphertext, decrypttext);
```

# 方式二：服务调用
```java
public class test {
    
    @Autowired
    StringEncryptor stringEncryptor;

    public void a() {
        // 待加密字符串
        String txt = "abcd";
        // 加密 
        String ciphertext = stringEncryptor.encrypt(txt);
        // 解密
        String decrypttext = stringEncryptor.decrypt(ciphertext);
        // 打印
        System.out.printf("原文:%s%n密文:%s%n解密:%s%n", txt, ciphertext, decrypttext);
    }
}
```

# 配置文件使用加密

* 首先通过上面方式加密的字符串，添加到配置文件中，那么我们程序Jasypt在启动的时候会自动解析出明文


application.yml
```
##############################################
# spring
##############################################
spring:
  # 数据源配置
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/test?serverTimezone=GMT%2B8&useSSL=false&useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true
    # 在使用的时候，您要使用ENC限定符字眼，告诉jasypt需要解析他
    username: ENC(x0jY0g478sC7qMnAMOmvgA==)
    password: ENC(5YfhKmsp4Ugsq70ookGQfw==)
```


