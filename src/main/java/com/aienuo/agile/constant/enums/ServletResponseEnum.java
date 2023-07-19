package com.aienuo.agile.constant.enums;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * ArgumentResponseEnum<br>
 * 参数校验异常返回结果<br>
 * </p>
 *
 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
 * @author XinLau
 * @version 1.0
 * @since 2021年01月20日 13:24
 */
@Getter
@AllArgsConstructor
public enum ServletResponseEnum {
    /**
     * 参数校验异常
     */
    MethodArgumentNotValidException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    /**
     * 参数类型不匹配异常
     */
    MethodArgumentTypeMismatchException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    /**
     * 错误请求异常
     */
    MissingServletRequestPartException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    /**
     * 错误请求异常
     */
    MissingPathVariableException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    /**
     * 参数绑定错误请求异常
     */
    BindException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    /**
     * 请求参数丢失异常
     */
    MissingServletRequestParameterException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    /**
     * 类型不匹配异常
     */
    TypeMismatchException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    /**
     * 请求绑定异常
     */
    ServletRequestBindingException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    /**
     * 请求消息不可读异常
     */
    HttpMessageNotReadableException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    /**
     * 没有处理器匹配异常
     */
    NoHandlerFoundException(4404, "", HttpServletResponse.SC_NOT_FOUND),
    /**
     * 没有对应的请求处理器异常
     */
    NoSuchRequestHandlingMethodException(4404, "", HttpServletResponse.SC_NOT_FOUND),
    /**
     * 不支持请求方法异常
     */
    HttpRequestMethodNotSupportedException(4405, "", HttpServletResponse.SC_METHOD_NOT_ALLOWED),
    /**
     * 请求内容无法接受异常
     */
    HttpMediaTypeNotAcceptableException(4406, "", HttpServletResponse.SC_NOT_ACCEPTABLE),
    /**
     * 请求内容不支持异常
     */
    HttpMediaTypeNotSupportedException(4415, "", HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE),
    /**
     * 请求转换异常
     */
    ConversionNotSupportedException(4500, "", HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
    /**
     * http消息不可写异常
     */
    HttpMessageNotWritableException(4500, "", HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
    /**
     * 异步请求超时异常
     */
    AsyncRequestTimeoutException(4503, "", HttpServletResponse.SC_SERVICE_UNAVAILABLE);

    /**
     * 返回码，目前与{@link #statusCode}相同
     */
    private int code;
    /**
     * 返回信息，直接读取异常的message
     */
    private String message;
    /**
     * HTTP状态码
     */
    private int statusCode;
}
