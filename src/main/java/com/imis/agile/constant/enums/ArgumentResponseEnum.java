package com.imis.agile.constant.enums;

import com.imis.agile.constant.CommonExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * ArgumentResponseEnum<br>
 * 参数校验异常返回结果<br>
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年01月20日 13:24
 */
@Getter
@AllArgsConstructor
public enum ArgumentResponseEnum implements CommonExceptionAssert {
    /**
     * 200 - 逻辑错误类型   00 - 模块   01 - 功能   00 - 错误码
     */
    COMMON_EXCEPTION(200010000, "参数校验异常"),
    NULL_POINTER_EXCEPTION(200010001, "空指针异常"),

    /**
     * 导入导出 - Excel
     */
    EXCEL_IMPORT_ERR(200020301, "Excel 导入失败，{0}"),

    /**
     * 文件 上传下载
     */
    FILE_ADD_ERR(200030101, "{0} 上传失败"),
    FILE_DOWNLOAD_ERR(200030202, "文件下载失败 {0}"),

    /**
     * 增删改查、导入、导出 参数 校验 不通过
     */
    INSERT_PARAMETERS_VALID_ERROR(200040101, "{0} - 添加失败，{1}"),
    DELETE_PARAMETERS_VALID_ERROR(200040102, "{0} - 删除失败，{1}"),
    UPDATE_PARAMETERS_VALID_ERROR(200040103, "{0} - 更新失败，{1}"),
    SELECT_PARAMETERS_VALID_ERROR(200040104, "{0} - 查询失败，{1}"),
    IMPORT_PARAMETERS_VALID_ERROR(200040105, "{0} - 导入失败，{1} ： {2}"),
    EXPORT_PARAMETERS_VALID_ERROR(200040106, "{0} - 导出失败，{1}"),

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
