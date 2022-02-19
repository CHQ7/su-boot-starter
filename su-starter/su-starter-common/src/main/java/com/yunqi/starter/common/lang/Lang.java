package com.yunqi.starter.common.lang;


import com.yunqi.starter.common.lang.stream.StringOutputStream;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

/**
 * 这些帮助函数让 Java 的某些常用功能变得更简单
 * Created by @author JsckChin on 2022/1/22
 */
public abstract class Lang {

    private static final Pattern IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

    private static final Pattern IPV6_STD_PATTERN = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");

    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");


    public static boolean isIPv4Address(final String input) {
        return IPV4_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6StdAddress(final String input) {
        return IPV6_STD_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6HexCompressedAddress(final String input) {
        return IPV6_HEX_COMPRESSED_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6Address(final String input) {
        return isIPv6StdAddress(input) || isIPv6HexCompressedAddress(input);
    }

    /**
     * <p>
     *     将一个抛出对象的异常堆栈，显示成一个字符串
     * </p>
     * @param e 抛出对象
     * @return 异常堆栈文本
     */
    public static String getStackTrace(Throwable e) {
        StringBuilder sb = new StringBuilder();
        StringOutputStream sbo = new StringOutputStream(sb);
        PrintStream ps = new PrintStream(sbo);
        e.printStackTrace(ps);
        ps.flush();
        return sbo.getStringBuilder().toString();
    }

    /**
     * 用运行时异常包裹抛出对象，如果抛出对象本身就是运行时异常，则直接返回。
     *
     * 如果是 InvocationTargetException，那么将其剥离，只包裹其 TargetException
     * @param e 抛出对象
     * @return  运行时异常
     */
    public static RuntimeException wrapThrow(Throwable e) {
        if (e instanceof RuntimeException)
            return (RuntimeException) e;
        if (e instanceof InvocationTargetException)
            return wrapThrow(((InvocationTargetException) e).getTargetException());
        return new RuntimeException(e);
    }

    /**
     * 数组是否为非空
     *
     * @param <T>   数组元素类型
     * @param array 数组
     * @return 是否为非空
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return (null != array && array.length != 0);
    }


    /**
     * 获得访问者的IP地址, 反向代理过的也可以获得
     *
     * @param request 请求的req对象
     * @return 来源ip
     */
    public static String getIP(HttpServletRequest request) {
        if (request == null)
            return "";
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        if (Strings.isBlank(ip))
            return "";
        if (isIPv4Address(ip) || isIPv6Address(ip)) {
            return ip;
        }
        return "";
    }


}
