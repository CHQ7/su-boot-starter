# 组件名称

su-starter-jasypt

# 组件说明

Jasypt安全框架加密组件

# 作用域

所有项目

# 使用说明

Jasypt是一个加密库对配置文件进行加密

# 依赖关系

| 名称         | 技术       |
|------------|----------|
| lombok |      |
| jasypt-spring-boot-starter     |   Jasypt安全框架加密组件  |

# 使用方式

# 方式一：通过工具类调用
```java
public class test {
    public static void main(String[] args) {
        // 待加密字符串
        String txt = "abcd";
        // 加密
        String ciphertext = JasyptUtil.encrypt(txt);
        // 解密
        String decrypttext = JasyptUtil.decrypt(ciphertext);
        // 打印
        System.out.printf("原文:%s%n密文:%s%n解密:%s%n", txt, ciphertext, decrypttext);
    }
}
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

# 配置文件加密

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


# 更新日志

2022-02-16
* 优化:设置默认StringEncryptor
* 优化:优化注入方式


