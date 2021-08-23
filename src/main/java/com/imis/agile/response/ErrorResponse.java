package com.imis.agile.response;

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
public class ErrorResponse extends BaseResponse {

    public ErrorResponse() {
    }

    public ErrorResponse(int code, String message) {
        super(code, message);
    }

}