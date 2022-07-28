package com.yunqi.starter.weixin.provider;

/**
 * Created by @author CHQ on 2022/7/27
 */
public interface WxLoginApi {

    /**
     * 返回重定向到微信登录页面的URL
     * @param state
     * @return
     */
    String qrconnect(String redirect_uri, String scope, String state);

    /**
     * 获取微信公众号授权链接
     * @param redirect_uri  授权后重定向的回调链接地址
     * @param scope         应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过 openid 拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息 ）
     * @param state         重定向后会带上 state 参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
     * @return              授权链接
     */
    String authorize(String redirect_uri, String scope, String state);

    /**
     * 根据code换取网页授权access_token
     * @param code      网页授权code
     * @return          JSON 数据包
     * <br>
     * 例：<br>
     * {<br>
     *   "access_token":"ACCESS_TOKEN",<br>
     *   "expires_in":7200,<br>
     *   "refresh_token":"REFRESH_TOKEN",<br>
     *   "openid":"OPENID",<br>
     *   "scope":"SCOPE"<br>
     * }
     */
    WxResp access_token(String code);

    /**
     * 刷新网页授权access_token
     * @param refresh_token     填写通过access_token获取到的refresh_token参数
     * @return                  JSON 数据包
     * <br>
     * 例：<br>
     * {<br>
     *   "access_token":"ACCESS_TOKEN",<br>
     *   "expires_in":7200,<br>
     *   "refresh_token":"REFRESH_TOKEN",<br>
     *   "openid":"OPENID",<br>
     *   "scope":"SCOPE"<br>
     * }
     */
    WxResp refresh_token(String refresh_token);

    /**
     * 验证授权凭证（access_token）是否有效
     * @param openid        用户的唯一标识
     * @param access_token  网页授权access_token
     * @return              JSON数据包
     * <br>
     * 例：<br>
     * { "errcode":0,"errmsg":"ok"}
     */
    WxResp auth(String openid, String access_token);

    /**
     * 获取用户信息
     * @param openid        用户的唯一标识
     * @param access_token  网页授权access_token
     * @return              用户信息JSON数据包
     * <br>
     * 例：<br>
     * {<br>
     *   "openid": "OPENID",<br>
     *   "nickname": NICKNAME,<br>
     *   "sex": 1,<br>
     *   "province":"PROVINCE",<br>
     *   "city":"CITY",<br>
     *   "country":"COUNTRY",<br>
     *   "headimgurl":"https://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46",<br>
     *   "privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],<br>
     *   "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"<br>
     * }
     */
    WxResp userinfo(String openid, String access_token);
}
