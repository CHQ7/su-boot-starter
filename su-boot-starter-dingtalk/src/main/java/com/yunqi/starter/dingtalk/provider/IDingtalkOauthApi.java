package com.yunqi.starter.dingtalk.provider;

import com.yunqi.starter.common.lang.util.NutMap;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 钉钉身份验证(免等)接口
 * Created by @author CHQ on 2022/11/16
 */
public interface IDingtalkOauthApi {

    /**
     * 身份验证(免等) -> 通过免登码获取用户信息
     * <br>
     * <p> 在第三方企业应用免登和企业内部应用免登场景中，开发者需要使用本接口通过access_token和免登接口中获取的code来获取用户userid </p>
     *
     * @param code      免登授权码，获取方式。请参考小程序免登授权码、微应用免登授权码。此授权码五分钟内有效，且只能使用一次。
     * @return          登录信息
     */
    NutMap auth(String code);

    /**
     * 身份验证(免等) -> 根据sns临时授权码获取用户信息
     * <br>
     * <p> 调用本接口根据sns临时授权码获取用户信息 </p>
     *
     * @param code      免登授权码，获取方式。请参考小程序免登授权码、微应用免登授权码。此授权码五分钟内有效，且只能使用一次。
     * @return          登录信息
     */
    NutMap authsns(String code) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException;


}
