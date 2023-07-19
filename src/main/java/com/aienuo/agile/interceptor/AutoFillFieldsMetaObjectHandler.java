package com.aienuo.agile.interceptor;

import com.aienuo.agile.constant.CommonConstant;
import com.aienuo.agile.constant.DataBaseConstant;
import com.aienuo.agile.constant.enums.CommonResponseEnum;
import com.aienuo.agile.util.AgileUtil;
import com.aienuo.agile.util.JwtUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
        if (AgileUtil.isEmpty(getFieldValByName(DataBaseConstant.CREATE_BY, metaObject))) {
            String token = getHttpServletRequest().getHeader(CommonConstant.X_ACCESS_TOKEN);
            CommonResponseEnum.TOKEN_500.assertNotEmpty(token);
            // 获取 Token 中的 username
            username = JwtUtil.getUsername(token);
        }
        CommonResponseEnum.TOKEN_500.assertNotEmpty(username);
        log.debug("执行 插入数据时的填充策略");
        this.strictInsertFill(metaObject, DataBaseConstant.CREATE_BY, String.class, username);
        this.strictInsertFill(metaObject, DataBaseConstant.CREATE_TIME, java.time.LocalDateTime.class, java.time.LocalDateTime.now());
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
        if (AgileUtil.isEmpty(getFieldValByName(DataBaseConstant.UPDATE_BY, metaObject))) {
            String token = getHttpServletRequest().getHeader(CommonConstant.X_ACCESS_TOKEN);
            CommonResponseEnum.TOKEN_500.assertNotEmpty(token);
            // 获取 Token 中的 username
            String username = JwtUtil.getUsername(token);
            CommonResponseEnum.TOKEN_500.assertNotEmpty(username);
            this.strictUpdateFill(metaObject, DataBaseConstant.UPDATE_BY, String.class, username);
        }
        log.debug("执行 更新数据时的填充策略");
        this.strictUpdateFill(metaObject, DataBaseConstant.UPDATE_TIME, java.time.LocalDateTime.class, java.time.LocalDateTime.now());
    }

}
