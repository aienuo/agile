package com.aienuo.agile.module.system.model.vo;

import com.aienuo.agile.constant.base.BaseTree;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 菜单权限 - 树查询 - 返回值
 * </p>
 *
 * @author XinLau
 * @since 2020-03-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "菜单权限 - 树查询 - 返回值", description = "菜单权限 - 树查询 - 返回值")
public class MenuTreeInfoVO extends BaseTree implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 是否叶子节点（0-不是，1-是）
     */
    @Schema(title = "是否叶子节点（0-不是，1-是）", description = "是否叶子节点（0-不是，1-是）")
    private Integer leafType;

    /**
     * 菜单类型（0-一级菜单，1-子菜单，2-按钮权限）
     */
    @Schema(title = "菜单类型（0-一级菜单，1-子菜单，2-按钮权限）", description = "菜单类型（0-一级菜单，1-子菜单，2-按钮权限）")
    private Integer menuType;

    /**
     * 菜单名称
     */
    @Schema(title = "菜单名称", description = "菜单名称")
    private String name;

    /**
     * 菜单图标
     */
    @Schema(title = "菜单图标", description = "菜单图标")
    private String icon;

    /**
     * 路径
     */
    @Schema(title = "路径", description = "路径")
    private String url;

    /**
     * 组件
     */
    @Schema(title = "组件", description = "组件")
    private String component;

    /**
     * 菜单排序
     */
    @Schema(title = "菜单排序", description = "菜单排序")
    private Double sortNo;

    /**
     * 权限策略（0-显示，1-禁用）
     */
    @Schema(title = "权限策略（0-显示，1-禁用）", description = "权限策略（0-显示，1-禁用）")
    private Integer status;

    /**
     * 菜单描述
     */
    @Schema(title = "菜单描述", description = "菜单描述")
    private String description;

}
