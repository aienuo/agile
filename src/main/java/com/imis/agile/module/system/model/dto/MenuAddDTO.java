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
 * 菜单权限 - 权限添加参数
 * </p>
 *
 * @author XinLau
 * @since 2020-03-18
 */
@Data
@ApiModel(value = "菜单权限 - 权限添加参数", description = "菜单权限 - 权限添加参数")
@EqualsAndHashCode(callSuper = false)
public class MenuAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 父记录id
     */
    @ApiModelProperty(value = "父记录Id")
    private Long parentId;

    /**
     * 是否叶子节点（0-不是，1-是）
     */
    @ApiModelProperty(value = "是否叶子节点（0-不是，1-是）")
    private Integer leafType;

    /**
     * 菜单类型（0-一级菜单，1-子菜单，2-按钮权限）
     */
    @ApiModelProperty(value = "菜单类型（0-一级菜单，1-子菜单，2-按钮权限）", required = true)
    @NotNull
    @Min(0)
    @Max(2)
    private Integer menuType;

    /**
     * 按钮类型（primary / success / warning / danger / info / text）
     */
    @ApiModelProperty(value = "按钮类型（primary / success / warning / danger / info / text）")
    private String buttonType;

    /**
     * 按钮尺寸（medium / small / mini）
     */
    @ApiModelProperty(value = "按钮尺寸（medium / small / mini）")
    private String buttonSize;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称", required = true)
    @NotBlank
    private String name;

    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标")
    private String icon;

    /**
     * 路径
     */
    @ApiModelProperty(value = "路径")
    private String url;

    /**
     * 组件
     */
    @ApiModelProperty(value = "组件")
    private String component;

    /**
     * 菜单排序
     */
    @ApiModelProperty(value = "菜单排序", required = true)
    @NotNull
    private Double sortNo;

    /**
     * 按钮权限冻结状态(0-正常，1-冻结）
     */
    @ApiModelProperty(value = "按钮权限冻结状态(0-正常，1-冻结）", required = true)
    @NotNull
    private Integer status;

    /**
     * 菜单描述
     */
    @ApiModelProperty(value = "菜单描述")
    private String description;

}
