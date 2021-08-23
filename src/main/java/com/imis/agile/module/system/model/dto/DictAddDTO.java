package com.imis.agile.module.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
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
@ApiModel(value = "字典 - 项 - 添加参数", description = "字典 - 项 - 添加参数")
@EqualsAndHashCode(callSuper = false)
public class DictAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称", required = true)
    @NotBlank(message = "字典名称不能为空")
    private String dictName;

    /**
     * 字典编码
     */
    @ApiModelProperty(value = "字典编码", required = true)
    @NotBlank(message = "字典编码不能为空")
    private String dictCode;

    /**
     * 字典类型（0-String，1-Number）
     */
    @ApiModelProperty(value = "字典类型（0-String，1-Number）")
    private Integer dictType;

    /**
     * 冻结状态(0-正常，1-冻结）
     */
    @ApiModelProperty(value = "冻结状态(0-正常，1-冻结）")
    private Integer status;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

}
