package com.yunqi.starter.log.aop;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.yunqi.starter.common.lang.Lang;
import com.yunqi.starter.common.lang.mvc.Mvcs;
import com.yunqi.starter.common.utils.IPUtil;
import com.yunqi.starter.log.annotation.SLog;
import com.yunqi.starter.log.configuration.LogProperties;
import com.yunqi.starter.log.model.SysLog;
import com.yunqi.starter.log.provider.ISysLogProvider;
import com.yunqi.starter.security.utils.SecurityUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.nutz.json.Json;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by @author JsckChin on 2022/2/17
 */
@Aspect
@Component
public class SLogAspect {

    private final LogProperties properties;

    @Resource
    private ISysLogProvider iSysLogProvider;

    public SLogAspect(LogProperties properties) {
        this.properties = properties;
    }

    /** 定义AOP签名 (切入所有使用SLog鉴权注解的方法) */
    public static final String POINTCUT_SIGN = "@annotation(com.yunqi.starter.log.annotation.SLog)";

    /** 声明AOP签名 */
    @Pointcut(POINTCUT_SIGN)
    public void pointcut() {}


    /**
     * 处理正常结果
     * @param joinPoint 切入点
     * @param res       返回结果
     */
    @AfterReturning(pointcut = "pointcut()", returning = "res")
    public void  doAroundReturning(JoinPoint joinPoint, Object res) {
        handleLog(joinPoint, null, res);
    }


    /**
     * 处理异常结果
     * @param joinPoint 切入点
     * @param ex        返回结果
     */
    @AfterThrowing(pointcut = "pointcut()", throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint, Exception ex) {
        handleLog(joinPoint, ex, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception ex, Object res){
        // 是否开启
        if (properties != null && properties.isEnabled()) {
            // 获取注解
            SLog slog = getAnnotationLog(joinPoint);
            if(slog == null){
                return;
            }
            // ========================================== 开始请求日志 ==========================================
            long beginTime = System.currentTimeMillis();

            // *========数据库日志=========* //
            SysLog sysLog = new SysLog();
            // 设置操作模块
            // 设置业务类型
            // 获取请求方法
            // 设置请求状态
            sysLog.setTag(slog.tag());
            sysLog.setMsg(slog.type().getLabel());
            sysLog.setSrc(joinPoint.getSignature().getDeclaringTypeName() + "#" + joinPoint.getSignature().getName());
            sysLog.setStatus(0);
            // 获取请求终端信息
            terminal(sysLog, slog.param());
            // 是否需要保存请求结果
            if(slog.result()){
                sysLog.setResult(Json.toJson(res));
            }
            // 记录异常消息
            if(ex != null){
                sysLog.setStatus(1);
                sysLog.setResult( StrUtil.sub(ex.getMessage(),0, 2000));
            }
            // 处理耗时(毫秒)
            sysLog.setExecuteTime(System.currentTimeMillis() - beginTime);
            // ========================================== 结束请求日志 ==========================================
            // 记录日志
            iSysLogProvider.saveLog(sysLog);

        }
    }

    /**
     * 转换request请求参数
     * @param paramMap  获取请求参数
     * @return          请求参数字符串
     */
    private String convertMap(Map<String, String[]> paramMap) {
        Map<String, String> param = new HashMap<>();
        for (String key : paramMap.keySet()) {
            param.put(key, paramMap.get(key)[0]);
        }
        return Json.toJson(param);
    }


    /**
     * 获取Slog
     * @param joinPoint 切入点
     * @return          Slog
     */
    private SLog getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(SLog.class);
        }
        return null;
    }

    /**
     * 获取请求终端信息
     * @param sysLog  系统日志
     * @param isParam 是否需要保存请求参数
     */
    private void terminal(SysLog sysLog, boolean isParam){
        // 获取请求
        HttpServletRequest req  = Mvcs.getReq();
        // 获取终端信息
        final UserAgent ua = UserAgentUtil.parse(req.getHeader("User-Agent"));

        sysLog.setUrl(req.getRequestURI()); // 获取请求地址
        sysLog.setMethod(req.getMethod());  // 获取请求方式
        sysLog.setBrowser(ua.getBrowser().getName() + "_" + ua.getVersion()); // 设置终端信息
        sysLog.setOs(ua.getPlatform().getName() + "_"  + ua.getOsVersion());
        // 是否需要保存请求参数
        if(isParam){
            sysLog.setParam(convertMap(req.getParameterMap()));
        }
        // 设置创建时间
        sysLog.setCreatedAt(System.currentTimeMillis());
        sysLog.setUpdatedAt(System.currentTimeMillis());
        // 设置操作人
        sysLog.setCreatedById(SecurityUtil.getUserId());
        sysLog.setCreatedBy(SecurityUtil.getUserNickname());
        sysLog.setUpdatedById(SecurityUtil.getUserId());
        sysLog.setUpdatedBy(SecurityUtil.getUserNickname());
        String ip = Lang.getIP(req);
        // 获取IP
        sysLog.setIp(ip);
        // 获取IP归属地
        sysLog.setLocation(IPUtil.getIPAddress(ip));
    }
}
