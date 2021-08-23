package com.imis.agile.constant;

/**
 * <p>
 * IResponseEnum<br>
 * 异常返回码枚举接口<br>
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年01月20日 13:24
 */
public interface IResponseEnum {
    /**
     * 获取返回码
     *
     * @return 返回码
     */
    int getCode();

    /**
     * 获取返回信息
     *
     * @return 返回信息
     */
    String getMessage();

}
