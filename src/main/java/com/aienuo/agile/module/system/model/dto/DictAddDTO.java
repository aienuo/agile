package com.aienuo.agile.module.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 字典 - 项
 * </p>
 *
 * @author XinLau
 * @since 2020-03-23
 */
@Data
@Schema(title = "字典 - 项 - 添加参数", description = "字典 - 项 - 添加参数")
@EqualsAndHashCode(callSuper = false)
public class DictAddDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典名称
     */
    @Schema(title = "字典名称", description = "字典名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String dictName;

    /**
     * 字典编码
     */
    @Schema(title = "字典编码", description = "字典编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String dictCode;

    /**
     * 字典类型（0-String，1-Number）
     */
    @Schema(title = "字典类型（0-String，1-Number）", description = "字典类型（0-String，1-Number）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Min(0)
    @Max(1)
    private Integer dictType;

    /**
     * 冻结状态(0-正常，1-冻结）
     */
    @Schema(title = "冻结状态(0-正常，1-冻结）", description = "冻结状态(0-正常，1-冻结）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Min(0)
    @Max(1)
    private Integer status;

    /**
     * 描述
     */
    @Schema(title = "描述", description = "描述")
    private String description;

}
