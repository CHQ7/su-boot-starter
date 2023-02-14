package com.yunqi.starter.security.configuration;

import cn.dev33.satoken.stp.StpInterface;
import com.yunqi.starter.security.provider.IAuthProvider;
import org.nutz.lang.Strings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限认证接口，实现此接口即可集成权限认证功能
 *
 * 用于提供用户的权限信息（permissionList 和 roleList）。
 * Created by @author CHQ on 2022/2/16
 */
@Configuration
@ConditionalOnClass(StpInterface.class) // 当 StpInterface 类存在时，才会创建该配置类的实例
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private IAuthProvider iAuthProvider;


    /**
     * 返回指定账号id所拥有的权限码集合
     *
     * @param loginId  账号id
     * @param loginType 账号类型
     * @return 该账号id具有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return iAuthProvider.getPermissionList(Strings.sNull(loginId));
    }

    /**
     * 返回指定账号id所拥有的角色标识集合
     *
     * @param loginId  账号id
     * @param loginType 账号类型
     * @return 该账号id具有的角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return iAuthProvider.getRoleList(Strings.sNull(loginId));
    }
}
