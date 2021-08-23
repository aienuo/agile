package com.imis.agile.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * CommonResponse<br>
 * 通用返回结果<br>
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年01月20日 13:24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommonResponse<T> extends BaseResponse {

    /**
     * 数据列表
     */
    protected T data;

    public CommonResponse() {
        super();
    }

    public CommonResponse(T data) {
        super();
        this.data = data;
    }

}
