package com.imis.agile.module.api.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.imis.agile.constant.base.BaseTreeVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 菜单权限 - 登录 - 返回值
 * </p>
 *
 * @author XinLau
 * @since 2020-03-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "菜单权限 - 登录 - 返回值", description = "菜单权限 - 登录 - 返回值")
public class MenuTreeVO extends BaseTreeVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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

}
