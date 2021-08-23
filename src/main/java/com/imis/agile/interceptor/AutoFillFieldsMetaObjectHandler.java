package com.imis.agile.interceptor;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.imis.agile.constant.CommonConstant;
import com.imis.agile.constant.enums.CommonResponseEnum;
import com.imis.agile.util.AgileUtil;
import com.imis.agile.util.JwtUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * AutoFillFieldsMetaObjectHandler<br>
 * 自动填充字段元对象处理程序<br>
 * 自动注入创建人、创建时间、修改人、修改时间
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年01月20日 13:24
 */
@Component
public class AutoFillFieldsMetaObjectHandler implements MetaObjectHandler {

    /**
     * 获取 HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 插入数据时的填充策略
     *
     * @param metaObject - 元对象
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2021/1/20 13:30
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        String username = StringPool.EMPTY;
        if (AgileUtil.isEmpty(getFieldValByName(com.imis.agile.constant.DataBaseConstant.CREATE_BY, metaObject))) {
            String token = getHttpServletRequest().getHeader(CommonConstant.X_ACCESS_TOKEN);
            if (AgileUtil.isEmpty(token)) {
                token = JwtUtil.getCookieValue(CommonConstant.X_COOKIE_NAME);
            }
            CommonResponseEnum.TOKEN_500.assertNotEmpty(token);
            // 获取 Token 中的 username
            username = JwtUtil.getUsername(token);
        }
        this.setFieldValByName(com.imis.agile.constant.DataBaseConstant.CREATE_BY, username, metaObject);
        this.setFieldValByName(com.imis.agile.constant.DataBaseConstant.CREATE_TIME, java.time.LocalDateTime.now(), metaObject);
    }

    /**
     * 更新数据时的填充策略
     *
     * @param metaObject - 元对象
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2021/1/20 13:30
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        String username = StringPool.EMPTY;
        if (AgileUtil.isEmpty(getFieldValByName(com.imis.agile.constant.DataBaseConstant.UPDATE_BY, metaObject))) {
            String token = getHttpServletRequest().getHeader(CommonConstant.X_ACCESS_TOKEN);
            if (AgileUtil.isEmpty(token)) {
                token = JwtUtil.getCookieValue(CommonConstant.X_COOKIE_NAME);
            }
            CommonResponseEnum.TOKEN_500.assertNotEmpty(token);
            // 获取 Token 中的 username
            username = JwtUtil.getUsername(token);
        }
        this.setFieldValByName(com.imis.agile.constant.DataBaseConstant.UPDATE_BY, username, metaObject);
        this.setFieldValByName(com.imis.agile.constant.DataBaseConstant.UPDATE_TIME, java.time.LocalDateTime.now(), metaObject);
    }

}
