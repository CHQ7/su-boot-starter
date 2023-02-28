### su-boot-starter-database

`su-boot-starter-database` 提供了基于 Nutz 的数据库访问功能，支持 CRUD 操作和 SQL 查询操作。用户可以通过继承 `su-boot-starter-database` 提供的基础 DAO 类，或者通过注入 Nutz 提供的 DAO 实现类，快速实现数据库访问操作。

### 安装

通过Maven仓库安装，在pom.xml文件中加入以下内容：

```xml
<dependency>
  <groupId>com.yunqi.starter</groupId>
  <artifactId>su-boot-starter-database</artifactId>
  <version>1.0.0</version>
</dependency>
```

### 使用

1.在`application.yml`配置文件中，添加`数据库`的配置：

```yml
spring:
  # 数据源配置
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/数据库?serverTimezone=GMT%2B8&useSSL=false&useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true
    username: 数据库用户名
    password: 数据库密码
su:
  database:
    runtime:
      basepackage: 实体包名
```

2.创建实体类自动在数据库中创建表：

```java
import com.yunqi.starter.database.model.BaseModel;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;

@Data
@Table("user")
@TableMeta("{'mysql-charset':'utf8mb4'}")
public class User extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Name
    @Column
    @Comment("ID")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    @Prev(els = { @EL("uuid()") })
    private String id;

    @Column
    @Comment("昵称")
    @ColDefine(type = ColType.VARCHAR, width = 120)
    private String nickname;

    @Column
    @Comment("手机")
    @ColDefine(type = ColType.VARCHAR, width = 11)
    private String mobile;
    
}
```

3.在服务类中调用，在需要使用`数据源`的地方，可以通过如下代码调用：

创建 UserServiceImpl 类:

```java
import com.yunqi.starter.database.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.nutz.dao.Cnd;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> {

    /**
     * 用户列表
     * @param query         请求参数
     * @return              分页列表
     */
    public Pagination<Member> list( QueryBodyquery query) {
        Cnd cnd =  Cnd.NEW();
        // 模糊查询:手机号
        if(Strings.isNotBlank(query.getString("mobile"))){
            cnd.and("mobile", "like", "%" + query.getString("mobile") + "%");
        }
        // 精确查询:等级
        if(Strings.isNotBlank(query.getString("levelId"))){
            cnd.and("levelId", "=", query.getInt("levelId"));
        }
        // 精确查询:状态
        if(Strings.isNotBlank(query.getString("status"))){
            cnd.and("status", "=", query.getBoolean("status"));
        }
        // 时间范围:根据时间戳范围查询
        if(Strings.isNotBlank(query.getString("beginTime")) || Strings.isNotBlank(query.getString("endTime"))){
            cnd.and("createdAt",">=", query.getString("beginTime"));
            cnd.and("createdAt","<=", query.getString("endTime"));
        }
        // 创建时间倒序
        cnd.desc("createdAt");
        return this.listPage(query.page(),query.pageSize(), cnd);
    }
    
    /**
     * 创建用户
     * @param user      新的用户
     * @return          用户
     */
    @Transactional
    public Member create(User user) {
        return this.insert(user);
    }


    /**
     * 更新用户
     * @param user      更新用户
     * @return          boolean
     */
    @Transactional
    public boolean update(User user) {
        return this.updateIgnoreNull(user) != 0;
    }

    /**
     * 删除用户
     * @param id    ID
     * @return      boolean
     */
    @Transactional
    public boolean deleteById(String id) {
        return this.delete(id) != 0;
    }

    /**
     * 批量删除用户
     * @param ids   ID数组字符串
     */
    @Transactional
    public void deleteByIds(String ids) {
        if (Strings.isNotBlank(ids)) {
            String[] arr = ids.split(",");
            this.delete(arr);
        }
    }
}
```
其中 `UserServiceImpl ` 是一个继承了 `su-boot-starter-database` 提供的基础CRUD 操作和 SQL 查询操作，实现了对 User 数据库表的操作，增删改查操作。

### 配置说明

| 名称                             | 默认值   | 备注             |
|--------------------------------|-------|----------------|
| enabled                        | true  | 是否开启组件         |
| log                            | false | 是否打印操作日志       |

### 全局配置说明
| 名称                             | 默认值   | 备注                  |
|--------------------------------|-------|---------------------|
| global.checkColumnNameKeyword	 | false | 是否检查字段为数据库的关键字      |
| global.forceWrapColumnName     | false | 是否把字段名用字符包裹来进行关键字逃逸 |
| global.forceUpperColumnName    | false | 是否把字段名给变成大写         |
| global.forceHumpColumnName	    | false |                     |
| global.defaultVarcharWidth     | 128   | varchar 字段的默认字段长度   |

### 运行配置说明
| 名称                   | 默认值   | 备注                 |
|----------------------|-------|--------------------|
| runtime.create       | true  | 自动建表               |
| runtime.migration    | true  | 自动变更               |
| runtime.basepackage  |       | 实体包名,必填不然扫描不到class |
| runtime.foceCreate   | false | 强制创建<删表重建>         |
| runtime.addColumn    | true  | 是否增加列              |
| runtime.deleteColumn | false | 是否删除列              |
| runtime.checkIndex   | false | 检查索引               |

### SQL模版配置说明
| 名称                 | 默认值   | 备注                            |
|--------------------|-------|-------------------------------|
| sqlTemplate.enable | false | 是否启用`SqlTemplate`             |
| sqlTemplate.type   | BEETL | 模版引擎类型,选项枚举`SqlTemplate.Type` |

### SQL管理器配置说明
| 名称               | 默认值 | 备注                       |
|------------------|-----|--------------------------|
| sqlManager.mode  |     | 模式,选项枚举`SqlManager.Mode` |
| sqlManager.paths |     | 路径列表                     |

### 拦截器配置说明
| 名称               | 默认值  | 备注     |
|------------------|------|--------|
| interceptor.time | true | sql 记时 |


### 注意事项

在使用 `su-boot-starter-database` 组件时，需要注意以下几点：

- 数据库的连接信息应该妥善保存，并遵循相关安全规则。
- 在数据库操作过程中，请注意数据的安全性。
- 在进行数据库操作时，请务必遵循 Nutz 的相关使用规则。
- 如果需要更改数据库连接信息，请修改配置文件中的相关信息并重新启动项目。


