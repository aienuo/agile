package com.imis.agile.module.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@ApiModel(value = "字典 - 值 - 添加参数", description = "字典 - 值 - 添加参数")
@EqualsAndHashCode(callSuper = false)
public class DictItemAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典 - 项编号
     */
    @ApiModelProperty(value = "字典 - 项编号", required = true)
    @NotNull(message = "字典 - 项编号不能为空")
    private Long dictId;

    /**
     * 父记录id
     */
    @ApiModelProperty(value = "父记录id")
    private Long parentId;

    /**
     * 字典项文本
     */
    @ApiModelProperty(value = "字典项文本", required = true)
    @NotBlank(message = "字典项文本不能为空")
    private String name;

    /**
     * 字典项值
     */
    @ApiModelProperty(value = "字典项值")
    private String value;

    /**
     * 禁用状态(true1-禁用，false0-不禁用）
     */
    @ApiModelProperty(value = "禁用状态(true1-禁用，false0-不禁用）")
    private Integer disabled;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", required = true)
    @NotNull(message = "排序号不能为空")
    private Double sortNo;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

}
