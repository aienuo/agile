package com.aienuo.agile.exception;

import com.aienuo.agile.constant.Assert;

import java.io.Serial;

/**
 * <p>
 * WrapMessageException<br>
 * 只包装了 错误信息 的 {@link RuntimeException}.<br>
 * 用于 {@link Assert} 中用于包装自定义异常信息
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年01月20日 13:24
 */
public class WrapMessageException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1512408043688042448L;

    /**
     * @param message - MESSAGE
     */
    public WrapMessageException(String message) {
        super(message);
    }

    /**
     * @param message   - MESSAGE
     * @param throwable - Throwable
     */
    public WrapMessageException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
