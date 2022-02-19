package com.yunqi.starter.security.configuration;

import cn.dev33.satoken.stp.StpInterface;
import com.yunqi.starter.security.provider.IAuthProvider;
import org.nutz.lang.Strings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by @author JsckChin on 2022/2/16
 */
@Configuration
@ConditionalOnClass(StpInterface.class)
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private IAuthProvider iAuthProvider;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return iAuthProvider.getPermissionList(Strings.sNull(loginId));
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return iAuthProvider.getRoleList(Strings.sNull(loginId));
    }
}
