package com.aienuo.agile.constant.base;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * <p>
 * 通用 业务处理类
 * </p>
 *
 * @author XinLau
 * @since 2020-03-27
 */
@Slf4j
public abstract class BaseBus {

    /**
     * 获取 HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取 HttpServletResponse
     */
    public static HttpServletResponse getHttpServletResponse() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

}
