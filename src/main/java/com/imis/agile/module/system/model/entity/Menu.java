package com.imis.agile.module.system.model.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.imis.agile.constant.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 菜单权限
 * </p>
 *
 * @author XinLau
 * @since 2020-03-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class Menu extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 父记录id
     */
    @TableField(value = "parent_id", updateStrategy = FieldStrategy.IGNORED)
    private Long parentId;

    /**
     * 是否叶子节点（0-不是，1-是）
     */
    @TableField(value = "leaf_type")
    private Integer leafType;

    /**
     * 菜单类型（0-一级菜单，1-子菜单，2-按钮权限）
     */
    @TableField(value = "menu_type")
    private Integer menuType;

    /**
     * 按钮类型（primary / success / warning / danger / info / text）
     */
    @TableField(value = "button_type")
    private String buttonType;

    /**
     * 按钮尺寸（medium / small / mini）
     */
    @TableField(value = "button_size")
    private String buttonSize;

    /**
     * 菜单名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 菜单图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 路径
     */
    @TableField(value = "url")
    private String url;

    /**
     * 组件
     */
    @TableField(value = "component")
    private String component;

    /**
     * 菜单排序
     */
    @TableField(value = "sort_no")
    private Double sortNo;

    /**
     * 按钮权限冻结状态(0-正常，1-冻结）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 菜单描述
     */
    @TableField(value = "description")
    private String description;

}