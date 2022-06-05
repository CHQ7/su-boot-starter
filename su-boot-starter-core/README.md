# su-boot-starter-core

公共组件包

# 使用说明

| 名称       | 技术       |
|----------|----------|
| constant | 全局变量     |
| Page     | 分页       |
| Result   | 统一数据返回格式 |
| utils    | 工具类      |
| lang     | Java函数糖  |
| json     | json函数   |

# 函数糖

我希望在 80% 以上的情况下，这些函数能让你有效的缩短你代码的体积，并且增加代码的可读性。

# Json

# 将Java对象转为一个`Json`字符串

```
Json.toJson(Object);
```

# `Json`字符串转Java对象, 分成几种情况

### 从来源看,一般无难度吧:
- 无Pojo类对应
```
Json.fromJson(source/*来源参数*/)
```
- 有Pojo类对应
```
Json.fromJson(Class<T> klass, source/*来源参数*/)
Json.fromJson(Type t, source/*来源参数*/)
```
- Pojo集合或Map嵌套
```
Json.fromJsonAsArray(Class<T> klass, source/*来源参数*/)
Json.fromJsonAsList(Class<T> klass, source/*来源参数*/)
Json.fromJsonAsMap(Class<T> klass, source/*来源参数*/)
```

# Base64编码
```
// 文本
String text = "Sa-Token 一个轻量级java权限认证框架";

// 使用Base64编码
Base64.encode(text);


// 使用Base64解码
Base64.decode(base64Text);

```

# 字符串操作

# 是否为空
- 校验是否空，返回true或者false
- isEmpty(null) => true
- isEmpty("") => true
- isEmpty(" \t\n") => false
- isEmpty("abc") => false

该方法建议用于工具类或任何可以预期的方法参数的校验中
```
Strings.isEmpty("")
```

# 是否为非空
- 校验是否非空，返回true或者false
- isNotEmpty(null) => false
- isNotEmpty("") => false
- isNotEmpty(" \t\n") => true
- isNotEmpty("abc") => true

该方法建议用于工具类或任何可以预期的方法参数的校验中
```
Strings.isNotEmpty("abc")
```

# 是否为空
- 校验是否空，返回true或者false
- isBlank(null) => true
- isBlank("") => true
- isBlank(" \t\n") => true
- isBlank("abc") => false

该方法建议仅对于客户端（或第三方接口）传入的参数使用该方法
```
Strings.isBlank("")
```

# 是否为非空
- 校验是否空，返回true或者false
- isNotBlank(null) => false
- isNotBlank("") => false
- isNotBlank(" \t\n") => false
- isNotBlank("abc") => true

该方法建议仅对于客户端（或第三方接口）传入的参数使用该方法
```
Strings.isNotBlank("")
```


# Unicode编码解码
- unicodeDecode("\\u5df2\\u89e3\\u5c01") => 已解封
```
Strings.unicodeDecode("\\u5df2\\u89e3\\u5c01")
```

# 是否为URL
- 校验是否URL，返回true或者false
```
Strings.isUrl("http://baidu.com")
```

# 按长度截取字符串（尾部补足）
- 正则为null或者""则不检查，返回true，内容为null返回false
- cutStr(5, "10000000", "9") => 10009
- cutStr(5, "10000000", "999") => 1000999
```
Strings.cutStr(5, "10000000", "999")
```

# 是否匹配正则
- 正则为null或者""则不检查，返回true，内容为null返回false
```
Strings.isMactchRegex(Pattern.compile("^[\\d]+$"), "1000")
```

# 截去最后一个字符
- removeLast("12345") => 1234
- removeLast("A") => ""
```
Strings.removeLast("12345")
```

# 截去最后一个字符(如果str中最后一个字符和 c一致,则删除,否则返回 str)
- removeLast("12345",'5') => "1234"
- removeLast("ABC",'B') => "ABC"
- removeLast("A",'B') => "A"
- removeLast("A",'A') => ""
```
Strings.removeLast("12345",'5')
```

# 是否手机号（中国）
- 校验是否手机号，返回true或者false
```
Strings.isMobile("13378111198")
```

# 是否身份证号（18位中国）
- 校验是否身份证号，返回true或者false
```
Strings.isCitizenId("110101199003071970")
```

# 是否为邮政编码（中国）
- 校验是否邮政编码，返回true或者false
```
Strings.isZipCode("610000")
```

# 是否为货币
- 校验是否货币，返回true或者false
```
Strings.isMoney("100.69")
```

# 是否为数字
- 校验是否数字，返回true或者false
```
Strings.isNumber("10069")
```

# 是否为邮箱
- 校验是否邮箱，返回true或者false
```
Strings.isEmail("123@qq.com")
```

# 是否为QQ号
- 校验是否QQ号，返回true或者false
```
Strings.isQQ("1259999")
```

# 是否为统一社会信用代码（18位）
- 校验是否统一社会信用代码，返回true或者false
```
Strings.isUSCC("91310000795641927D")
```

# 是否为银联卡号,银联卡规则62开头，卡号为16-19位数字
- 校验是否银联卡号，返回true或者false
```
Strings.isUnionPayCard("6212262201023557228")
```
