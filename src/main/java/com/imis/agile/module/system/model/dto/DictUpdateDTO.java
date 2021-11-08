package com.imis.agile.module.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@ApiModel(value = "字典 - 项 - 修改参数", description = "字典 - 项 - 修改参数")
@EqualsAndHashCode(callSuper = false)
public class DictUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典 - 项编号
     */
    @ApiModelProperty(value = "字典 - 项编号", required = true)
    @NotNull
    private Long id;

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称", required = true)
    @NotBlank
    private String dictName;

    /**
     * 字典编码
     */
    @ApiModelProperty(value = "字典编码", required = true)
    @NotBlank
    private String dictCode;

    /**
     * 字典类型（0-String，1-Number）
     */
    @ApiModelProperty(value = "字典类型（0-String，1-Number）", required = true)
    @NotNull
    @Min(0)
    @Max(1)
    private Integer dictType;

    /**
     * 冻结状态(0-正常，1-冻结）
     */
    @ApiModelProperty(value = "冻结状态(0-正常，1-冻结）", required = true)
    @NotNull
    @Min(0)
    @Max(1)
    private Integer status;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

}
