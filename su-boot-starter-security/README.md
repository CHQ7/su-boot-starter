# su-boot-starter-security

- `su-boot-starter-security` 是一个基于Spring Boot的安全解决方案，它为应用提供了一系列的安全特性，比如认证和授权。此组件的目的是简化安全配置，使开发人员可以专注于业务逻辑的开发，而不用担心安全问题。

# 快速入门

- [传送门](demo.md)

# 安装
- 通过Maven仓库安装，在pom.xml文件中加入以下内容：

```xml
<dependency>
  <groupId>com.yunqi.starter</groupId>
  <artifactId>su-boot-starter-security</artifactId>
  <version>1.0.0</version>
</dependency>
```

# 注解鉴权

注解鉴权 —— 优雅的将鉴权与业务代码分离！

- @RequiresAuthentication: 登录校验 —— 只有登录之后才能进入该方法。
- @RequiresRoles("admin"): 角色校验 —— 必须具有指定角色标识才能进入该方法。
- @RequiresPermissions("user:add"): 权限校验 —— 必须具有指定权限才能进入该方法。

# Session会话

- **Session是什么？**
- Session是会话中专业的数据缓存组件，通过 Session 我们可以很方便的缓存一些高频读写数据，提高程序性能，例如：

```
// 获取当前会话账号id, 如果未登录，则抛出异常
SecuritySessionUtil.getUserId()

// 获取当前会话的Session
SecuritySessionUtil.getSession()

// 获取当前会话账号
SecuritySessionUtil.getUserName()

// 设置当前会话账号
SecuritySessionUtil.setUserName(String username)

// 获取当前会话昵称
SecuritySessionUtil.getUserNickname()

// 设置当前会话昵称
SecuritySessionUtil.setUserNickname(String nickname)
```

# 配置权限认证提供者

> 因为每个项目的需求不同，其权限设计也千变万化，因此 [ 获取当前账号权限码集合 ] 这一操作不可能内置到框架中， 所以 `su-boot-starter-security` 将此操作以接口的方式暴露给你，以方便你根据自己的业务逻辑进行重写。

- 你需要做的就是新建一个类，实现 `IAuthProvider ` 接口，并使用 @Component 注解注册为Spring Bean。例如以下代码：

```
/**
 * 自定义权限验证接口扩展
 */
@Component    // 保证此类被SpringBoot扫描，完成 `su-boot-starter-security` 的自定义权限验证扩展 
public class CustomProviderImpl implements IAuthProvider {

    /**
     * 返回一个账号所拥有的权限码集合 
     *
     * @param loginId  账号id
     * @return 该账号id具有的权限码集合
     */
    @Override
    public List<String> getPermissionList(String userId) {
       // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        List<String> list = new ArrayList<String>();    
        list.add("101");
        list.add("user.add");
        list.add("user.update");
        list.add("user.get");
        // list.add("user.delete");
        list.add("art.*");
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     *
     * @param loginId  账号id
     * @return 该账号id具有的角色标识集合
     */
    @Override
    public List<String> getRoleList(String userId) {
        return sysUserService.getRoleList(userId);
    }

}

```

# 登录认证

- 来个小测试，加深一下理解：

```
/**
 * 登录测试 
 */
@RestController
@RequestMapping("/admin/")
public class LoginController {

    // 测试登录  ---- http://localhost:8081/admin/doLogin?name=zhang&pwd=123456
    @RequestMapping("doLogin")
    public Result doLogin(String name, String pwd) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对 
        if("zhang".equals(name) && "123456".equals(pwd)) {
            SecurityUtil.login(10001);
            return Result.ok("登录成功");
        }
        return Result.error("登录失败");
    }

    // 查询登录状态  ---- http://localhost:8081/admin/isLogin
    @RequestMapping("isLogin")
    public Result isLogin() {
        return Result.ok("是否登录：" + SecurityUtil.isLogin());
    }

    // 查询 Token 信息  ---- http://localhost:8081/admin/tokenInfo
    @RequestMapping("tokenInfo")
    public Result tokenInfo() {
        return Result.data(SecurityUtil.getTokenInfo());
    }

    // 测试注销  ---- http://localhost:8081/admin/logout
    @RequestMapping("logout")
    public Result logout() {
        SecurityUtil.logout();
        return Result.ok();
    }

}
```




