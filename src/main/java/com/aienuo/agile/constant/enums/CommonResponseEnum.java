package com.aienuo.agile.constant.enums;


import com.aienuo.agile.constant.CommonExceptionAssert;
import com.aienuo.agile.exception.BaseException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * CommonResponseEnum<br>
 * 通用返回结果
 * </p>
 * 一般对应于{@link BaseException}，系统封装的工具出现异常
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月31日 16:50
 */
@Getter
@AllArgsConstructor
public enum CommonResponseEnum implements CommonExceptionAssert {

    /**
     * 成功
     */
    SUCCESS(6666, "SUCCESS"),
    /**
     * 服务器遇到错误，无法完成请求。服务器异常，无法识别的异常，尽可能对通过判断减少未定义异常抛出
     */
    ERROR_500(9999, "ERROR"),
    TOKEN_500(9998, "Token 过期"),
    PERMISSIONS_500(9997, "未授权"),

    ;

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回消息
     */
    private String message;

}
