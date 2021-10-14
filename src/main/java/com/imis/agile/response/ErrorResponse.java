package com.imis.agile.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.imis.agile.constant.enums.CommonResponseEnum;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * ErrorResponse<br>
 * 错误返回结果
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月31日 16:50
 */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "错误返回结果", description = "错误返回结果")
public class ErrorResponse extends BaseResponse {

    public ErrorResponse() {
        // 默认创建失败的回应
        super(CommonResponseEnum.ERROR_500);
    }

    public ErrorResponse(String message) {
        // 默认创建失败的回应
        super(CommonResponseEnum.ERROR_500, message);
    }

    public ErrorResponse(int code, String message) {
        super(code, message);
    }

}