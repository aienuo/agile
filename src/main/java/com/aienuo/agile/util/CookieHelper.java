package com.aienuo.agile.util;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Cookie工具类
 * <p>
 * 注意：在cookie的名或值中不能使用分号（;）、逗号（,）、等号（=）以及空格
 * </p>
 *
 * @author XinLau
 * @since 2014-5-8
 */
@Slf4j
public class CookieHelper {

    /**
     * 立即删除
     */
    public final static int CLEAR_IMMEDIATELY_REMOVE = 0;

    /**
     * 根据cookieName获取Cookie
     *
     * @param request    - HttpServletRequest
     * @param cookieName - Cookie name
     * @return Cookie
     */
    public static Cookie findCookieByName(final HttpServletRequest request, final String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * 根据 cookieName 清空 Cookie【默认域下】
     *
     * @param response   - HttpServletResponse
     * @param cookieName - Cookie name
     */
    public static void clearCookieByName(final HttpServletResponse response, final String cookieName) {
        Cookie cookie = new Cookie(cookieName, StringPool.EMPTY);
        cookie.setMaxAge(CLEAR_IMMEDIATELY_REMOVE);
        response.addCookie(cookie);
        response.addHeader(cookieName, StringPool.EMPTY);
    }

    /**
     * <p>
     * 清除指定doamin的所有Cookie
     * </p>
     *
     * @param request  - HttpServletRequest
     * @param response - HttpServletResponse
     * @param domain   - Cookie所在的域
     * @param path     - Cookie 路径
     */
    public static void clearAllCookie(final HttpServletRequest request, final HttpServletResponse response, final String domain, final String path) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            clearCookie(response, cookie.getName(), domain, path);
        }
        log.info("clearAllCookie in domain " + domain);
    }

    /**
     * 根据cookieName清除指定Cookie
     *
     * @param request    - HttpServletRequest
     * @param response   - HttpServletResponse
     * @param cookieName - cookie name
     * @param domain     - Cookie所在的域
     * @param path       - Cookie 路径
     * @return boolean
     */
    public static Boolean clearCookieByName(final HttpServletRequest request, final HttpServletResponse response, final String cookieName, final String domain, final String path) {
        // 验证 Cookie 是否存在
        Cookie cookieByName = findCookieByName(request, cookieName);
        if (cookieByName != null) {
            return clearCookie(response, cookieName, domain, path);
        }
        return Boolean.FALSE;
    }

    /**
     * 清除指定Cookie 等同于 clearCookieByName(...)
     * <p>
     * 该方法不判断Cookie是否存在，因此不对外暴露防止Cookie不存在异常.
     *
     * @param response   - HttpServletResponse
     * @param cookieName - Cookie name
     * @param domain     - Cookie所在的域
     * @param path       - Cookie 路径
     * @return Boolean tru - 成功
     */
    private static Boolean clearCookie(final HttpServletResponse response, final String cookieName, final String domain, final String path) {
        try {
            Cookie cookie = new Cookie(cookieName, StringPool.EMPTY);
            cookie.setMaxAge(CLEAR_IMMEDIATELY_REMOVE);
            if (AgileUtil.isNotEmpty(domain)) {
                cookie.setDomain(domain);
            }
            cookie.setPath(path);
            response.addCookie(cookie);
            response.addHeader(cookieName, StringPool.EMPTY);
            log.debug("clear Cookie " + cookieName);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("clear Cookie [{}] is exception!\n {}", cookieName, e.toString());
            return Boolean.FALSE;
        }
    }

    /**
     * 添加 Cookie
     *
     * @param response - HttpServletResponse
     * @param name     - 名称
     * @param value    - 内容
     * @param domain   - 所在域
     * @param path     - 域名路径
     * @param maxAge   - 生命周期参数
     * @param httpOnly - 只读
     * @param secure   - Https协议下安全传输
     */
    public static void addCookie(final HttpServletResponse response, final String name, final String value, final String domain, final String path, final Integer maxAge, final Boolean httpOnly, final Boolean secure) {
        Cookie cookie = new Cookie(name, value);
        StringBuilder stringBuilder = new StringBuilder(name).append(StringPool.EQUALS).append(value).append(StringPool.SEMICOLON);
        // Max-Age属性指定从现在开始 Cookie 存在的秒数
        cookie.setMaxAge(maxAge);
        if (maxAge >= 0) {
            stringBuilder.append("Max-Age=").append(maxAge).append(StringPool.SEMICOLON);
        }
        if (AgileUtil.isNotEmpty(domain)) {
            // 不设置该参数默认 当前所在域
            cookie.setDomain(domain);
            stringBuilder.append("domain=").append(domain).append(StringPool.SEMICOLON);
        }
        // 只有获取该 Cookie 所在路径，才能实现对应的修改
        cookie.setPath(AgileUtil.isEmpty(path) ? StringPool.SLASH : path);
        stringBuilder.append("path=").append(path).append(StringPool.SEMICOLON);
        if (secure) {
            // Cookie 只在Https协议下传输设置
            cookie.setSecure(true);
            stringBuilder.append("secure;");
        }
        if (httpOnly) {
            // HttpOnly属性指定该 Cookie 无法通过 JavaScript 脚本拿到
            cookie.setHttpOnly(true);
            stringBuilder.append("HTTPOnly;");
        }
        // 依次取得 Cookie 中的名称、值、 最大生存时间、路径、域和是否为安全协议信息
        response.addHeader(name, stringBuilder.toString());
        // 设置 Cookie
        response.addCookie(cookie);
    }

}
