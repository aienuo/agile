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
 * 在线开发 - 数据库表字段信息
 * </p>
 *
 * @author XinLau
 * @since 2020-10-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("onl_table_field")
public class TableFields extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 数据库属性 **/
    /**
     * 表ID
     */
    @TableField(value = "table_head_id")
    private Long tableHeadId;
    /**
     * 字段名
     */
    @TableField(value = "field_name")
    private String fieldName;
    /**
     * 字段名称
     */
    @TableField(value = "field_txt")
    private String fieldTxt;
    /**
     * 原字段名
     */
    @TableField(value = "field_name_old")
    private String fieldNameOld;
    /**
     * 是否主键（0否 1是）
     */
    @TableField(value = "is_key")
    private Integer isKey;
    /**
     * 是否允许为空（0否 1是）
     */
    @TableField(value = "is_null")
    private Integer isNull;
    /**
     * 数据库字段类型
     */
    @TableField(value = "field_type")
    private String fieldType;
    /**
     * 数据库字段长度
     */
    @TableField(value = "field_length")
    private Integer fieldLength;
    /**
     * 小数点后位数
     */
    @TableField(value = "field_point_length")
    private Integer fieldPointLength;
    /**
     * 表字段默认值
     */
    @TableField(value = "field_default_value")
    private String fieldDefaultValue;
    /**
     * 排序
     */
    @TableField(value = "sort_no")
    private Double sortNo;
    /**
     * 字段说明
     */
    @TableField(value = "description")
    private String description;

    /* 页面属性 **/
    /**
     * 查询显示（0否 1是）
     */
    @TableField(value = "query_show")
    private Integer queryShow;
    /**
     * 新增显示（0否 1是）
     */
    @TableField(value = "insert_show")
    private Integer insertShow;
    /**
     * 更新显示（0否 1是）
     */
    @TableField(value = "update_show")
    private Integer updateShow;
    /**
     * 列表显示（0否 1是）
     */
    @TableField(value = "table_show")
    private Integer tableShow;
    /**
     * 只读（0否 1是）
     */
    @TableField(value = "only_read")
    private Integer onlyRead;
    /**
     * 排序（0否 1是）
     */
    @TableField(value = "sort_flag")
    private Integer sortFlag;
    /**
     * 控件类型
     */
    @TableField(value = "control_type")
    private String controlType;
    /**
     * 控件长度
     */
    @TableField(value = "control_length")
    private Integer controlLength;
    /**
     * 查询模式
     */
    @TableField(value = "query_mode")
    private String queryMode;

    /* 校验规则 */
    /**
     * 验证规则
     */
    @TableField(value = "validation_rules")
    private String validationRules;

    /* 外键关联 */
    /**
     * 外键主表名
     */
    @TableField(value = "main_table_name")
    private String mainTableName;
    /**
     * 外键主键字段
     */
    @TableField(value = "main_table_field")
    private String mainTableField;

    /* 查询配置 */
    /**
     * 字典表表名
     */
    @TableField(value = "dict_table_name")
    private String dictTableName;
    /**
     * 字典Code字段
     */
    @TableField(value = "dict_code_field")
    private String dictCodeField;
    /**
     * 字典Text字段
     */
    @TableField(value = "dict_text_field")
    private String dictTextField;

    /**
     * 是否同步数据库（1未同步；0已同步）
     */
    @TableField(value = "synchronize")
    private Integer synchronize;

}
