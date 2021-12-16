package com.imis.agile.interceptor;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.imis.agile.constant.CommonConstant;
import com.imis.agile.constant.enums.CommonResponseEnum;
import com.imis.agile.util.AgileUtil;
import com.imis.agile.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * AutoFillFieldsMetaObjectHandler<br>
 * 自动填充字段处理程序<br>
 * 自动注入创建人、创建时间、修改人、修改时间
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年01月20日 13:24
 */
@Slf4j
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
            CommonResponseEnum.TOKEN_500.assertNotEmpty(token);
            // 获取 Token 中的 username
            username = JwtUtil.getUsername(token);
        }
        CommonResponseEnum.TOKEN_500.assertNotEmpty(username);
        log.debug("执行 插入数据时的填充策略");
        this.strictInsertFill(metaObject, com.imis.agile.constant.DataBaseConstant.CREATE_BY, String.class, username);
        this.strictInsertFill(metaObject, com.imis.agile.constant.DataBaseConstant.CREATE_TIME, java.time.LocalDateTime.class, java.time.LocalDateTime.now());
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
        if (AgileUtil.isEmpty(getFieldValByName(com.imis.agile.constant.DataBaseConstant.UPDATE_BY, metaObject))) {
            String token = getHttpServletRequest().getHeader(CommonConstant.X_ACCESS_TOKEN);
            CommonResponseEnum.TOKEN_500.assertNotEmpty(token);
            // 获取 Token 中的 username
            String username = JwtUtil.getUsername(token);
            CommonResponseEnum.TOKEN_500.assertNotEmpty(username);
            this.strictUpdateFill(metaObject, com.imis.agile.constant.DataBaseConstant.UPDATE_BY, String.class, username);
        }
        log.debug("执行 更新数据时的填充策略");
        this.strictUpdateFill(metaObject, com.imis.agile.constant.DataBaseConstant.UPDATE_TIME, java.time.LocalDateTime.class, java.time.LocalDateTime.now());
    }

}
