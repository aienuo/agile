package com.aienuo.agile.constant.base;

import com.aienuo.agile.constant.IResponseEnum;
import com.aienuo.agile.constant.enums.CommonResponseEnum;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * BaseResponse<br>
 * 基础返回结果
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年01月20日 13:24
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 返回码
     */
    @Schema(title = "返回码")
    protected int code;
    /**
     * 返回消息
     */
    @Schema(title = "返回消息")
    protected String message;

    public BaseResponse() {
        // 默认创建成功的回应
        this(CommonResponseEnum.SUCCESS);
    }

    /**
     * @param responseEnum - IResponseEnum
     */
    public BaseResponse(IResponseEnum responseEnum) {
        this(responseEnum.getCode(), responseEnum.getMessage());
    }

    /**
     * @param responseEnum - IResponseEnum
     */
    public BaseResponse(IResponseEnum responseEnum, String message) {
        this(responseEnum.getCode(), responseEnum.getMessage() + StringPool.COLON + message);
    }

    /**
     * @param code    -CODE
     * @param message - MESSAGE
     */
    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
