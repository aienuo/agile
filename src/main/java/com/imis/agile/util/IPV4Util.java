package com.imis.agile.util;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * IPV4Util<br>
 * IP地址 工具类
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年04月19日 10:08
 */
@Slf4j
public class IPV4Util {

    public final static String LOCAL_IP = "127.0.0.1";

    public final static String LOCAL_ADDRESS = "0:0:0:0:0:0:0:1";

    /**
     * 获取IP地址
     * <p>
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     */
    private static final String[] HEADERS_TO_TRY = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR", "X-Real-IP"};

    /**
     * @param checkIp - 被测试IP
     * @return Boolean - false - 标识IP存在问题
     */
    private static Boolean isNotUnknown(final String checkIp) {
        return AgileUtil.isNotEmpty(checkIp) && !"unknown".equalsIgnoreCase(checkIp);
    }

    /**
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     *
     * @param ip - 获得的IP地址
     * @return String - 第一个非unknown IP地址
     */
    private static String getMultistageReverseProxyIp(String ip) {
        // 多级反向代理检测
        if (null != ip && ip.indexOf(StringPool.COMMA) > 0) {
            String[] ips = ip.trim().split(StringPool.COMMA);
            for (String subIp : ips) {
                if (isNotUnknown(subIp)) {
                    ip = subIp;
                    // 循环处理逻辑在干同一件事情，取到值终止执行
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * getClientIpAddress 获取客户端ip地址(可以穿透代理)
     *
     * @param request - HttpServletRequest
     * @return String
     * @author XinLau
     * @since 2019年6月4日下午4:54:01
     */
    public static String getClientIpAddress(final HttpServletRequest request) {
        String ip = null;
        for (String header : HEADERS_TO_TRY) {
            // 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
            String ipByHeader = request.getHeader(header);
            if (isNotUnknown(ipByHeader)) {
                ip = ipByHeader;
                // 循环处理逻辑在干同一件事情，取到值终止执行
                break;
            }
        }
        if (null == ip) {
            // request.getRemoteAddr()获取IP地址 优先级别最低
            ip = request.getRemoteAddr();
        }
        if (!isNotUnknown(ip)) {
            // 兜底，防止IP为 null
            return StringPool.EMPTY;
        }
        if (LOCAL_ADDRESS.equals(ip)) {
            return LOCAL_IP;
        }
        // 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
        return getMultistageReverseProxyIp(ip);
    }

}
