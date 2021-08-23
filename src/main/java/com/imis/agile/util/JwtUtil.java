package com.imis.agile.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.imis.agile.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * <p>
 * JwtUtil<br>
 * JWT工具类
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2019年03月26日 11:11
 */
@Slf4j
public class JwtUtil {

    /**
     * 获取 HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取 HttpServletResponse
     */
    public static HttpServletResponse getHttpServletResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 校验Token是否正确
     *
     * @param token  - 密钥
     * @param secret - 用户的密码
     * @return boolean - 是否正确
     */
    public static Boolean verify(final String token, final String username, final String secret) {
        try {
            // 根据密码生成JWT效验器
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).withClaim("username", username).build();
            // 效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     *
     * @param token - 密钥
     * @return Token中包含的用户名
     */
    public static String getUsername(final String token) {
        try {
            return JWT.decode(token).getClaim("username").asString();
        } catch (JWTDecodeException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获得Token是否到期
     *
     * @param token - 密钥
     * @return Boolean - Token是否到期
     */
    public static Boolean isDue(final String token) {
        try {
            Date date = JWT.decode(token).getExpiresAt();
            return date.before(new Date());
        } catch (JWTDecodeException e) {
            log.error(e.getMessage(), e);
            return Boolean.TRUE;
        }
    }

    /**
     * 生成签名,EXPIRE_TIME后过期
     *
     * @param username - 登录账号
     * @param secret   - 登录密码
     * @return 加密的token
     */
    public static String sign(final String username, final String secret) {
        // 毫秒级的时间处理 附带 username 信息
        return JWT.create()
                .withClaim("username", username)
                // 毫秒级的时间处理
                .withExpiresAt(new Date(System.currentTimeMillis() + CommonConstant.EXPIRE_TIME))
                // 加密算法
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 从session中获取变量
     *
     * @param key - 变量名
     * @return String - 变量值
     */
    public static String getSessionData(String key) {
        //${myVar}%
        //得到${} 后面的值
        String moshi = StringPool.EMPTY;
        if (key.contains(StringPool.RIGHT_BRACE)) {
            moshi = key.substring(key.indexOf(StringPool.RIGHT_BRACE) + 1);
        }
        String returnValue = null;
        if (key.contains(StringPool.HASH_LEFT_BRACE)) {
            key = key.substring(2, key.indexOf(StringPool.RIGHT_BRACE));
        }
        if (AgileUtil.isNotEmpty(key)) {
            HttpSession session = getHttpServletRequest().getSession();
            returnValue = (String) session.getAttribute(key);
        }
        //结果加上${} 后面的值
        if (returnValue != null) {
            returnValue = returnValue + moshi;
        }
        return returnValue;
    }

    /**
     * 从 HttpServletRequest 中获取 Cookie
     *
     * @param cookieName - 变量名
     * @return Cookie - Cookie
     */
    public static Cookie getCookie(final String cookieName) {
        Cookie[] cookies = getHttpServletRequest().getCookies();
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
     * 从Cookie中获取变量
     *
     * @param cookieName - 变量名
     * @return String - 变量值
     */
    public static String getCookieValue(final String cookieName) {
        String cookieValue = StringPool.EMPTY;
        Cookie cookie = getCookie(cookieName);
        if (cookie != null) {
            cookieValue = cookie.getValue();
        }
        return cookieValue;
    }

    /**
     * 设置Cookie变量
     *
     * @param cookieName - 变量名
     * @param value      - 变量值
     */
    public static void setCookie(final String cookieName, final String value) {
        // Max-Age属性指定从现在开始 Cookie 存在的秒数 - 一个小时
        setCookie(cookieName, value, CommonConstant.MAX_AGE, Boolean.FALSE);
    }

    /**
     * 设置Cookie变量
     *
     * @param cookieName   - 变量名
     * @param value        - 变量值
     * @param cookieMaxAge - Max-Age属性指定从现在开始 Cookie 存在的秒数
     * @param secure       - HttpOnly属性指定该 Cookie 无法通过 JavaScript 脚本拿到
     */
    public static void setCookie(final String cookieName, final String value, final int cookieMaxAge, final Boolean secure) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setMaxAge(cookieMaxAge);
        cookie.setHttpOnly(secure);
        cookie.setPath(StringPool.SLASH);
        getHttpServletResponse().addCookie(cookie);
    }

    /**
     * 更新 Cookie 中变量的值
     *
     * @param cookieName - 变量名
     * @param value      - 变量值
     */
    public static void updateCookieValue(final String cookieName, final String value) {
        // Max-Age属性指定从现在开始 Cookie 存在的秒数 - 一个小时
        updateCookieValue(cookieName, value, CommonConstant.MAX_AGE, Boolean.FALSE);
    }

    /**
     * 更新 Cookie 中变量的值
     *
     * @param cookieName   - 变量名
     * @param value        - 变量值
     * @param cookieMaxAge - Max-Age属性指定从现在开始 Cookie 存在的秒数
     * @param secure       - HttpOnly属性指定该 Cookie 无法通过 JavaScript 脚本拿到
     */
    public static void updateCookieValue(final String cookieName, final String value, final int cookieMaxAge, final Boolean secure) {
        Cookie cookie = getCookie(cookieName);
        if (cookie != null) {
            cookie.setValue(value);
            cookie.setMaxAge(cookieMaxAge);
            cookie.setHttpOnly(secure);
            // 只有获取该cookie所在路径，才能实现对应的修改
            cookie.setPath(StringPool.SLASH);
        }
        getHttpServletResponse().addCookie(cookie);
    }

    /**
     * 删除Cookie变量
     *
     * @param cookieName - 变量名
     */
    public static void deleteCookie(String cookieName) {
        updateCookieValue(cookieName, null, 0, Boolean.TRUE);
    }

}
