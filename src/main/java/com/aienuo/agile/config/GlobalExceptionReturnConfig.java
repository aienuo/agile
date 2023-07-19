package com.aienuo.agile.config;

import com.aienuo.agile.constant.CommonConstant;
import com.aienuo.agile.constant.enums.CommonResponseEnum;
import com.aienuo.agile.constant.enums.ServletResponseEnum;
import com.aienuo.agile.exception.BaseException;
import com.aienuo.agile.response.ErrorResponse;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Objects;

/**
 * <p>
 * GlobalExceptionReturnConfig<br>
 * 全局返回值统一封装(包括异常)
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月31日 16:50
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionReturnConfig {

    /**
     * 生产环境
     */
    private final static String ENV_PROD = "prod";

    /**
     * 当前环境
     */
    @Value("${spring.profiles.active}")
    private String profile;

    /**
     * 用于解析消息的策略接口，支持此类消息的参数化和国际化
     */
    @Resource
    private MessageSource messageSource;

    /**
     * 获取 HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取国际化消息
     *
     * @param e - 异常
     * @return String - 国际化消息
     */
    private String getMessage(final BaseException e) {
        String code = CommonConstant.RESPONSE_I18N_PREFIX + e.getResponseEnum().getCode();
        return messageSource.getMessage(code, e.getArgs(), e.getMessage(), getHttpServletRequest().getLocale());
    }

    /**
     * 包装绑定异常结果
     *
     * @param bindingResult - 绑定结果
     * @return ErrorResponse - 错误返回结果
     */
    private ErrorResponse wrapperBindingResult(final BindingResult bindingResult) {
        StringBuilder message = new StringBuilder();
        bindingResult.getAllErrors().forEach(
                error -> {
                    message.append(StringPool.COMMA).append(StringPool.SPACE);
                    if (error instanceof FieldError) {
                        message.append(((FieldError) error).getField()).append(StringPool.COLON).append(StringPool.SPACE);
                    }
                    message.append(error.getDefaultMessage() == null ? StringPool.EMPTY : error.getDefaultMessage());
                }
        );
        return new ErrorResponse(message.substring(2));
    }

    /**
     * Controller上一层相关异常
     *
     * @param e - 异常
     * @return ErrorResponse - 错误返回结果
     */
    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            ConstraintViolationException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class
    })
    @ResponseBody
    public ErrorResponse handleServletException(Exception e) {
        log.error(e.getMessage(), e);
        int code = CommonResponseEnum.ERROR_500.getCode();
        try {
            ServletResponseEnum servletExceptionEnum = ServletResponseEnum.valueOf(e.getClass().getSimpleName());
            code = servletExceptionEnum.getCode();
        } catch (IllegalArgumentException e1) {
            log.error("class [{}] not defined in enums {}", e.getClass().getName(), ServletResponseEnum.class.getName());
        }
        if (ENV_PROD.equals(profile)) {
            // 当为生产环境, 不适合把具体的异常信息展示给用户, 比如404
            BaseException baseException = new BaseException(CommonResponseEnum.ERROR_500);
            String message = this.getMessage(baseException);
            return new ErrorResponse(message);
        }
        return new ErrorResponse(code, e.getMessage());
    }

    /**
     * 异常处理
     *
     * @param e - Exception
     * @return ErrorResponse - 错误返回结果
     */
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        log.error(e.getMessage(), e);
        if (ENV_PROD.equals(profile)) {
            // 当为生产环境, 不适合把具体的异常信息展示给用户, 比如数据库异常信息.
            BaseException baseException = new BaseException(CommonResponseEnum.ERROR_500);
            String message = this.getMessage(baseException);
            return new ErrorResponse(message);
        }
        return new ErrorResponse(e.getMessage());
    }

    /**
     * 处理自定义异常
     *
     * @param e - CustomizeException
     * @return ErrorResponse - 错误返回结果
     */
    @ExceptionHandler(BaseException.class)
    public ErrorResponse handleCustomizeException(BaseException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getResponseEnum().getCode(), this.getMessage(e));
    }

    /**
     * 方法参数无效异常(使用Spring Validator和Hibernate Validator这两套Validator来进行方便的参数校验)
     *
     * @param e - MethodArgumentNotValidException
     * @return ErrorResponse - 错误返回结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        // 从异常对象中拿到 BindingResult 对象
        return this.wrapperBindingResult(e.getBindingResult());
    }

    /**
     * 方法参数无效异常(使用Spring Validator和Hibernate Validator这两套Validator来进行方便的参数校验)
     *
     * @param e - BindException
     * @return ErrorResponse - 错误返回结果
     */
    @ExceptionHandler(BindException.class)
    public ErrorResponse bindExceptionHandler(BindException e) {
        log.error(e.getMessage(), e);
        // 从异常对象中拿到 BindingResult 对象
        return this.wrapperBindingResult(e);
    }

}
