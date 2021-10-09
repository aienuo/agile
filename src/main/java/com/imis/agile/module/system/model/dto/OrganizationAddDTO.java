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
 * OrganizationUpdateDTO<br>
 * 组织机构 - 添加参数
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年08月24日 15:03
 */
@Data
@ApiModel(value = "组织机构 - 添加参数", description = "组织机构 - 添加参数")
@EqualsAndHashCode(callSuper = false)
public class OrganizationAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 父记录id
     */
    @ApiModelProperty(value = "父记录Id")
    private Long parentId;

    /**
     * 组织机构名称
     */
    @ApiModelProperty(value = "组织机构名称", required = true)
    @NotBlank(message = "菜单名称不能为空")
    private String organizationName;

    /**
     * 排序号
     */
    @ApiModelProperty(value = "排序号", required = true)
    @NotNull(message = "排序号不能为空")
    private Double sortNo;

    /**
     * 冻结状态(0-正常，1-冻结）
     */
    @ApiModelProperty(value = "冻结状态(0-正常，1-冻结）", required = true)
    @NotNull(message = "冻结状态不能为空")
    private Integer status;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

}
