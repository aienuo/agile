package com.imis.agile.module.online.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.imis.agile.constant.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 在线开发 - 数据库表表信息
 * </p>
 *
 * @author XinLau
 * @since 2020-10-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("onl_table_head")
public class TableHead extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @TableField(value = "text_name")
    private String textName;

    /**
     * 表名
     */
    @TableField(value = "table_name")
    private String tableName;

    /**
     * 表类型（ 0单表、1主表、2附表）
     */
    @TableField(value = "table_type")
    private Integer tableType;

    /**
     * 是否分页（0否 1是）
     */
    @TableField(value = "paging")
    private Integer paging;

    /**
     * 新增表单风格（dialog-弹框、drawer-抽屉）
     */
    @TableField(value = "insert_style")
    private String insertStyle;

    /**
     * 更新表单风格（dialog-弹框、drawer-抽屉）
     */
    @TableField(value = "update_style")
    private String updateStyle;

    /**
     * 表版本
     */
    @TableField(value = "table_version")
    private Integer tableVersion;

    /**
     * 主题模板
     */
    @TableField(value = "theme_template")
    private String themeTemplate;

    /**
     * 表说明
     */
    @TableField(value = "description")
    private String description;

    /**
     * 是否同步数据库（1未同步；0已同步）
     */
    @TableField(value = "synchronize")
    private Integer synchronize;

}
