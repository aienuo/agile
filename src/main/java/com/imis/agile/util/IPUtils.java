package com.imis.agile.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * IPUtils<br>
 * IP地址 工具类
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年04月19日 10:08
 */
@Slf4j
public class IPUtils {

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
     * getClientIpAddress 获取客户端ip地址(可以穿透代理)
     *
     * @param request - HttpServletRequest
     * @return String
     * @author XinLau
     * @since 2019年6月4日下午4:54:01
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            for (String header : HEADERS_TO_TRY) {
                String ipByHeader = request.getHeader(header);
                if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                    ip = ipByHeader;
                }
            }
        }
        return ip;
    }

}
