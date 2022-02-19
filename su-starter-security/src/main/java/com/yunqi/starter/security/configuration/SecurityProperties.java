package com.yunqi.starter.security.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by @author JsckChin on 2022/2/16
 */
@Data
@ConfigurationProperties(prefix = "su.security")
public class SecurityProperties {

    /** 是否开启 */
    boolean enabled = true;

    /** token名称 (同时也是cookie名称) */
    private String tokenName = "token";

    /** token的长久有效期(单位:秒) 默认30天, -1代表永久 */
    private long timeout = 60 * 60 * 24 * 30;

    /**
     * token临时有效期 [指定时间内无操作就视为token过期] (单位: 秒), 默认-1 代表不限制
     * (例如可以设置为1800代表30分钟内无操作就过期)
     */
    private long activityTimeout = -1;

    /** 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录) */
    private Boolean isConcurrent = false;

    /** 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token) */
    private Boolean isShare = true;

    /** 是否尝试从请求体里读取token */
    private Boolean isReadBody = true;

    /** 是否尝试从header里读取token */
    private Boolean isReadHead = true;

    /** 是否尝试从cookie里读取token */
    private Boolean isReadCookie = true;

    /** token风格(默认可取值：uuid、simple-uuid、random-32、random-64、random-128、tik) */
    private String tokenStyle = "uuid";

    /** 默认dao层实现类中，每次清理过期数据间隔的时间 (单位: 秒) ，默认值30秒，设置为-1代表不启动定时清理 */
    private int dataRefreshPeriod = 30;

    /** 获取[token专属session]时是否必须登录 (如果配置为true，会在每次获取[token-session]时校验是否登录) */
    private Boolean tokenSessionCheckLogin = true;

    /** 是否打开自动续签 (如果此值为true, 框架会在每次直接或间接调用getLoginId()时进行一次过期检查与续签操作)  */
    private Boolean autoRenew = true;

    /** token前缀, 格式样例(satoken: Bearer xxxx-xxxx-xxxx-xxxx) */
    private String tokenPrefix;

    /** 是否在初始化配置时打印版本字符画 */
    private Boolean isPrint = false;

    /** 是否打印操作日志 */
    private Boolean isLog = true;
}
