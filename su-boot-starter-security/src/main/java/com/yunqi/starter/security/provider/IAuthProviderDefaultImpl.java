package com.yunqi.starter.security.provider;

import java.util.ArrayList;
import java.util.List;

/**
 * 对 IAuthProvider 接口默认的实现类
 * 如果开发者没有实现IAuthProvider接口，则使用此默认实现
 *
 * Created by @author CHQ on 2023/2/4
 */
public class IAuthProviderDefaultImpl implements IAuthProvider{

    @Override
    public List<String> getPermissionList(String loginId) {
        return new ArrayList<>();
    }

    @Override
    public List<String> getRoleList(String loginId) {
        return new ArrayList<>();
    }
    
}
