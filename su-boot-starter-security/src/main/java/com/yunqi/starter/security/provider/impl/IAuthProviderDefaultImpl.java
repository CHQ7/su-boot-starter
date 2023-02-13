package com.yunqi.starter.security.provider.impl;

import com.yunqi.starter.security.provider.IAuthProvider;
import java.util.Collections;
import java.util.List;

/**
 * 权限认证接口默认实现类
 * Created by @author CHQ on 2023/2/13
 */
public class IAuthProviderDefaultImpl implements IAuthProvider {
    @Override
    public List<String> getPermissionList(String loginId) {
        return Collections.emptyList();
    }

    @Override
    public List<String> getRoleList(String loginId) {
        return Collections.emptyList();
    }
}
