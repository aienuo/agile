package com.imis.agile.module.online.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * FieldAddDTO<br>
 * 在线开发 - 字段信息 - 新增
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年09月18日 16:01
 */
@Data
@ApiModel(value = "在线开发 - 字段信息 - 新增对象参数", description = "在线开发 - 字段信息 - 新增对象参数")
@EqualsAndHashCode(callSuper = false)
public class FieldAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 数据库属性 **/
    /**
     * 字段名
     */
    @ApiModelProperty(value = "字段名")
    private String fieldName;
    /**
     * 字段名称
     */
    @ApiModelProperty(value = "字段名称")
    private String fieldTxt;
    /**
     * 原字段名
     */
    @ApiModelProperty(value = "原字段名")
    private String fieldNameOld;
    /**
     * 是否主键（0否 1是）
     */
    @ApiModelProperty(value = "是否主键（0否 1是）")
    private Integer isKey;
    /**
     * 是否允许为空（0否 1是）
     */
    @ApiModelProperty(value = "是否允许为空（0否 1是）")
    private Integer isNull;
    /**
     * 数据库字段类型
     */
    @ApiModelProperty(value = "数据库字段类型")
    private String fieldType;
    /**
     * 数据库字段长度
     */
    @ApiModelProperty(value = "数据库字段长度")
    private Integer fieldLength;
    /**
     * 小数点后位数
     */
    @ApiModelProperty(value = "小数点后位数")
    private Integer fieldPointLength;
    /**
     * 表字段默认值
     */
    @ApiModelProperty(value = "表字段默认值")
    private String fieldDefaultValue;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Double sortNo;
    /**
     * 字段说明
     */
    @ApiModelProperty(value = "字段说明")
    private String description;

    /* 页面属性 **/
    /**
     * 查询显示（0否 1是）
     */
    @ApiModelProperty(value = "查询显示（0否 1是）")
    private Integer queryShow;
    /**
     * 新增显示（0否 1是）
     */
    @ApiModelProperty(value = "新增显示（0否 1是）")
    private Integer insertShow;
    /**
     * 更新显示（0否 1是）
     */
    @ApiModelProperty(value = "更新显示（0否 1是）")
    private Integer updateShow;
    /**
     * 列表显示（0否 1是）
     */
    @ApiModelProperty(value = "列表显示（0否 1是）")
    private Integer tableShow;
    /**
     * 只读（0否 1是）
     */
    @ApiModelProperty(value = "只读（0否 1是）")
    private Integer onlyRead;
    /**
     * 排序（0否 1是）
     */
    @ApiModelProperty(value = "排序（0否 1是）")
    private Integer sortFlag;
    /**
     * 控件类型
     */
    @ApiModelProperty(value = "控件类型")
    private String controlType;
    /**
     * 控件长度
     */
    @ApiModelProperty(value = "控件长度")
    private Integer controlLength;
    /**
     * 查询模式
     */
    @ApiModelProperty(value = "查询模式")
    private String queryMode;

    /* 校验规则 */
    /**
     * 验证规则
     */
    @ApiModelProperty(value = "验证规则")
    private String validationRules;

    /* 外键关联 */
    /**
     * 外键主表名
     */
    @ApiModelProperty(value = "外键主表名")
    private String mainTableName;
    /**
     * 外键主键字段
     */
    @ApiModelProperty(value = "外键主键字段")
    private String mainTableField;

    /* 查询配置 */
    /**
     * 字典表表名
     */
    @ApiModelProperty(value = "字典表表名")
    private String dictTableName;
    /**
     * 字典Code字段
     */
    @ApiModelProperty(value = "字典Code字段")
    private String dictCodeField;
    /**
     * 字典Text字段
     */
    @ApiModelProperty(value = "字典Text字段")
    private String dictTextField;


}
