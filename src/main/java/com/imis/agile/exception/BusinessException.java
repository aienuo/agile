package com.imis.agile.exception;


import com.imis.agile.constant.IResponseEnum;

/**
 * <p>
 * BusinessException<br>
 * 业务异常，业务处理时，出现异常，可以抛出该异常<br>
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年01月20日 13:24
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * @param responseEnum - 异常返回码枚举接口
     * @param args         - 参数
     * @param message      - MESSAGE
     */
    public BusinessException(IResponseEnum responseEnum, Object[] args, String message) {
        super(responseEnum, args, message);
    }

    /**
     * @param responseEnum - 异常返回码枚举接口
     * @param args         - 参数
     * @param message      - MESSAGE
     * @param throwable    - Throwable
     */
    public BusinessException(IResponseEnum responseEnum, Object[] args, String message, Throwable throwable) {
        super(responseEnum, args, message, throwable);
    }

}
