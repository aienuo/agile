package com.imis.agile.module.system.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.imis.agile.constant.base.BaseTreeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 菜单权限 - 数查询 - 返回值
 * </p>
 *
 * @author XinLau
 * @since 2020-03-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "菜单权限 - 数查询 - 返回值", description = "菜单权限 - 数查询 - 返回值")
public class MenuTreeInfoVO extends BaseTreeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否叶子节点（0-不是，1-是）
     */
    @TableField(value = "是否叶子节点（0-不是，1-是）")
    private Integer leafType;

    /**
     * 菜单类型（0-一级菜单，1-子菜单，2-按钮权限）
     */
    @ApiModelProperty(value = "菜单类型（0-一级菜单，1-子菜单，2-按钮权限）")
    private Integer menuType;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
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
    @ApiModelProperty(value = "菜单排序")
    private Double sortNo;

    /**
     * 权限策略（0-显示，1-禁用）
     */
    @ApiModelProperty(value = "权限策略（0-显示，1-禁用）")
    private Integer status;

    /**
     * 菜单描述
     */
    @ApiModelProperty(value = "菜单描述")
    private String description;

}
