package com.imis.agile.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.imis.agile.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;

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
     * @return Boolean - Token是否到期 TRUE-到期
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

}
