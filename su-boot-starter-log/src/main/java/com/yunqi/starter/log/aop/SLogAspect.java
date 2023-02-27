package com.yunqi.starter.log.aop;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.yunqi.starter.common.json.Json;
import com.yunqi.starter.common.lang.Lang;
import com.yunqi.starter.common.lang.Strings;
import com.yunqi.starter.common.lang.mvc.Mvcs;
import com.yunqi.starter.common.utils.IPUtil;
import com.yunqi.starter.log.configuration.LogProperties;
import com.yunqi.starter.log.model.SLogRecord;
import com.yunqi.starter.log.provider.ILogRecordProvider;
import com.yunqi.starter.log.provider.impl.DefaultLogRecordProviderImpl;
import com.yunqi.starter.security.utils.SecuritySessionUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by @author CHQ on 2022/2/17
 */

@Aspect
@Component
public class SLogAspect {

    private final LogProperties properties;

    @Autowired(required = false)
    private ILogRecordProvider logRecordProvider;

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
        execute(joinPoint, null, res);
    }


    /**
     * 处理异常结果
     * @param joinPoint 切入点
     * @param ex        返回结果
     */
    @AfterThrowing(pointcut = "pointcut()", throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint, Exception ex) {
        execute(joinPoint, ex, null);
    }

    /**
     * 处理日志
     * @param joinPoint
     * @param ex
     * @param res
     */
    protected void execute(final JoinPoint joinPoint, final Exception ex, Object res){

        // 如果为空或者默认实现，则不进行任何操作
        if(logRecordProvider == null  || logRecordProvider instanceof DefaultLogRecordProviderImpl){
            return;
        }

        // 是否开启
        if (properties != null && properties.isEnabled()) {

            // 获取注解
            com.yunqi.starter.log.annotation.SLog slog = getAnnotationLog(joinPoint);
            if(slog == null){
                return;
            }

            // ========================================== 开始请求日志 ==========================================
            long beginTime = System.currentTimeMillis();

            // *========数据库日志=========*
            // >> 设置操作模块
            // >> 设置业务类型
            // >> 获取请求方法
            // >> 设置请求状态
            // *=========================*
            SLogRecord sysLog = new SLogRecord();
            sysLog.setTag(slog.tag());
            sysLog.setMsg(Strings.isNotEmpty(slog.value())? slog.value() : slog.type().getLabel());
            sysLog.setSrc(joinPoint.getSignature().getDeclaringTypeName() + "#" + joinPoint.getSignature().getName());
            sysLog.setSuccess(true);

            // 获取请求终端信息
            terminal(sysLog);

            // 是否需要保存请求参数Body
            if(slog.param()){
                String param = "{}";
                // 验证长度是否为空
                if(joinPoint.getArgs().length != 0){
                    // 这里截取5000个字符
                    param = StrUtil.sub(Json.toJson(joinPoint.getArgs()[0], JsonFormat.compact()), 0, 5000);
                }
                sysLog.setParam(param);
            }

            // 是否需要保存请求结果
            if(slog.result()){
                sysLog.setResult(Json.toJson(res));
            }

            // 记录异常消息
            if(ex != null){
                sysLog.setSuccess(false);
                sysLog.setResult( StrUtil.sub(ex.getMessage(),0, 2000));
            }

            // 处理耗时(毫秒)
            sysLog.setExecuteTime(System.currentTimeMillis() - beginTime);

            // ========================================== 结束请求日志 ==========================================

            // 记录日志
            logRecordProvider.record(sysLog);
        }
    }

    /**
     * 转换request请求参数
     * @param paramMap  获取请求参数
     * @return          请求参数字符串
     */
    private String convertMap(Map<String, String[]> paramMap) {
        Map<String, String> param = paramMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]));
        return Json.toJson(param);
    }


    /**
     * 获取Slog
     * @param joinPoint 切入点
     * @return          Slog
     */
    private com.yunqi.starter.log.annotation.SLog getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(com.yunqi.starter.log.annotation.SLog.class);
        }
        return null;
    }

    /**
     * 获取请求终端信息
     * @param sysLog  系统日志
     */
    private void terminal(SLogRecord sysLog){
        // 获取请求
        HttpServletRequest req  = Mvcs.getReq();
        // 获取终端信息
        final UserAgent ua = UserAgentUtil.parse(req.getHeader("User-Agent"));

        sysLog.setUrl(req.getRequestURI()); // 获取请求地址
        sysLog.setMethod(req.getMethod());  // 获取请求方式
        sysLog.setBrowser(ua.getBrowser().getName() + "_" + ua.getVersion()); // 设置终端信息
        sysLog.setOs(ua.getPlatform().getName() + "_"  + ua.getOsVersion());

        // 设置创建时间
        sysLog.setCreatedAt(System.currentTimeMillis());
        sysLog.setUpdatedAt(System.currentTimeMillis());
        // 设置操作人
        sysLog.setCreatedById(SecuritySessionUtil.getUserId());
        sysLog.setCreatedBy(SecuritySessionUtil.getUserNickname());
        sysLog.setUpdatedById(SecuritySessionUtil.getUserId());
        sysLog.setUpdatedBy(SecuritySessionUtil.getUserNickname());
        String ip = Lang.getIP(req);
        // 获取操作地址
        sysLog.setIp(ip);
        // 获取操作地点
        sysLog.setLocation(IPUtil.getIPAddress(ip));
    }
}
