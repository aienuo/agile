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
 * 菜单权限 - 添加参数
 * </p>
 *
 * @author XinLau
 * @since 2020-03-18
 */
@Data
@Schema(title = "菜单权限 - 添加参数", description = "菜单权限 - 添加参数")
@EqualsAndHashCode(callSuper = false)
public class MenuAddDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 父记录id
     */
    @Schema(title = "父记录Id", description = "父记录Id")
    private Long parentId;

    /**
     * 是否叶子节点（0-不是，1-是）
     */
    @Schema(title = "是否叶子节点（0-不是，1-是）", description = "是否叶子节点（0-不是，1-是）")
    private Integer leafType;

    /**
     * 菜单类型（0-一级菜单，1-子菜单，2-按钮权限）
     */
    @Schema(title = "菜单类型（0-一级菜单，1-子菜单，2-按钮权限）", description = "菜单类型（0-一级菜单，1-子菜单，2-按钮权限）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Min(0)
    @Max(2)
    private Integer menuType;

    /**
     * 按钮类型（primary / success / warning / danger / info / text）
     */
    @Schema(title = "按钮类型（primary / success / warning / danger / info / text）", description = "按钮类型（primary / success / warning / danger / info / text）")
    private String buttonType;

    /**
     * 按钮尺寸（medium / small / mini）
     */
    @Schema(title = "按钮尺寸（medium / small / mini）", description = "按钮尺寸（medium / small / mini）")
    private String buttonSize;

    /**
     * 菜单名称
     */
    @Schema(title = "菜单名称", description = "菜单名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
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
    @Schema(title = "菜单排序", description = "菜单排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Double sortNo;

    /**
     * 按钮权限冻结状态(0-正常，1-冻结）
     */
    @Schema(title = "按钮权限冻结状态(0-正常，1-冻结）", description = "按钮权限冻结状态(0-正常，1-冻结）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Integer status;

    /**
     * 菜单描述
     */
    @Schema(title = "菜单描述", description = "菜单描述")
    private String description;

}
