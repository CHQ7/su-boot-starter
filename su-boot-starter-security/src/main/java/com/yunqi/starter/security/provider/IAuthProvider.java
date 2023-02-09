package com.yunqi.starter.security.provider;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限认证接口，实现此接口即可集成权限认证功能
 * Created by @author CHQ on 2022/2/16
 */
public interface IAuthProvider {

    /**
     * 返回指定账号id所拥有的权限码集合
     *
     * @param loginId  账号id
     * @return 该账号id具有的权限码集合
     */
    default List<String> getPermissionList(String loginId) {
        return new ArrayList<>();
    }

    /**
     * 返回指定账号id所拥有的角色标识集合
     *
     * @param loginId  账号id
     * @return 该账号id具有的角色标识集合
     */
    default List<String> getRoleList(String loginId){
        return new ArrayList<>();
    }
}
