package com.yunqi.starter.security.provider;

import java.util.List;

/**
 * Created by @author CHQ on 2022/2/16
 */
public interface IAuthProvider {

    List<String> getPermissionList(String userId);

    List<String> getRoleList(String userId);
}
