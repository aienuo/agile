package com.imis.agile.module.system.model.dto;

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
 * 字典 - 值
 * </p>
 *
 * @author XinLau
 * @since 2020-03-24
 */
@Data
@Schema(title = "字典 - 值 - 修改参数", description = "字典 - 值 - 修改参数")
@EqualsAndHashCode(callSuper = false)
public class DictItemUpdateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典 - 值编号
     */
    @Schema(title = "字典 - 值编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long id;

    /**
     * 字典 - 项编号
     */
    @Schema(title = "字典 - 项编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long dictId;

    /**
     * 父记录id
     */
    @Schema(title = "父记录id")
    private Long parentId;

    /**
     * 字典项文本
     */
    @Schema(title = "字典项文本", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String name;

    /**
     * 字典项值
     */
    @Schema(title = "字典项值")
    private String value;

    /**
     * 禁用状态(true1-禁用，false0-不禁用）
     */
    @Schema(title = "禁用状态(true1-禁用，false0-不禁用）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Min(0)
    @Max(1)
    private Integer disabled;

    /**
     * 排序
     */
    @Schema(title = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Double sortNo;

    /**
     * 描述
     */
    @Schema(title = "描述")
    private String description;

}
