package com.imis.agile.exception;

import com.imis.agile.constant.IResponseEnum;
import lombok.Getter;

/**
 * <p>
 * BaseException<br>
 * 基础异常类，所有自定义异常类都需要继承本类<br>
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年01月20日 13:24
 */
@Getter
@SuppressWarnings("unused")
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -8281828804189395973L;
    /**
     * 返回码
     */
    protected IResponseEnum responseEnum;
    /**
     * 异常消息参数
     */
    protected Object[] args;

    /**
     * @param responseEnum - 异常返回码枚举接口
     */
    public BaseException(IResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    /**
     * @param code    - CODE
     * @param message - MESSAGE
     */
    public BaseException(int code, String message) {
        super(message);
        this.responseEnum = new IResponseEnum() {
            @Override
            public int getCode() {
                return code;
            }

            @Override
            public String getMessage() {
                return message;
            }
        };
    }

    /**
     * @param responseEnum - 异常返回码枚举接口
     * @param args         - 参数
     * @param message      - MESSAGE
     */
    public BaseException(IResponseEnum responseEnum, Object[] args, String message) {
        super(message);
        this.responseEnum = responseEnum;
        this.args = args;
    }

    /**
     * @param responseEnum - 异常返回码枚举接口
     * @param args         - 参数
     * @param message      - MESSAGE
     * @param throwable    - Throwable
     */
    public BaseException(IResponseEnum responseEnum, Object[] args, String message, Throwable throwable) {
        super(message, throwable);
        this.responseEnum = responseEnum;
        this.args = args;
    }

}
