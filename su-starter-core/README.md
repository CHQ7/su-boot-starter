# su-starter-core

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




