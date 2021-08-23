package com.imis.agile.constant;

import com.imis.agile.exception.BaseException;

import java.text.MessageFormat;

/**
 * <p>
 * CommonExceptionAssert<br>
 * 通用异常断言<br>
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年01月20日 13:24
 */
public interface CommonExceptionAssert extends IResponseEnum, Assert {

    /**
     * <p> 创建 BaseException </p>
     *
     * @param args - 参数列表
     * @return BaseException - 基础异常类
     */
    @Override
    default BaseException newException(Object... args) {
        String msg = this.getMessage();
        if (args != null && args.length != 0) {
            msg = MessageFormat.format(this.getMessage(), args);
        }
        return new BaseException(this, args, msg);
    }

    /**
     * <p> 创建 BaseException </p>
     *
     * @param throwable - Throwable
     * @param args      - 参数列表
     * @return BaseException - 基础异常类
     */
    @Override
    default BaseException newException(Throwable throwable, Object... args) {
        String message = this.getMessage();
        if (args != null && args.length != 0) {
            message = MessageFormat.format(this.getMessage(), args);
        }
        return new BaseException(this, args, message, throwable);
    }

}