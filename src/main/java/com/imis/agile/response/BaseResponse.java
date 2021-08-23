package com.imis.agile.response;

import com.imis.agile.constant.IResponseEnum;
import com.imis.agile.constant.enums.CommonResponseEnum;
import lombok.Data;

/**
 * <p>
 * BaseResponse<br>
 * 基础返回结果<br>
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年01月20日 13:24
 */
@Data
public class BaseResponse {
    /**
     * 返回码
     */
    protected int code;
    /**
     * 返回消息
     */
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
     * @param code    -CODE
     * @param message - MESSAGE
     */
    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
