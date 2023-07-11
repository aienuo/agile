package com.imis.agile.module.system.model.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.imis.agile.constant.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 字典 - 值
 * </p>
 *
 * @author XinLau
 * @since 2020-03-24
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_item")
public class DictItem extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典id
     */
    @TableField(value = "dict_id")
    private Long dictId;

    /**
     * 父记录id
     */
    @TableField(value = "parent_id", updateStrategy = FieldStrategy.IGNORED)
    private Long parentId;

    /**
     * 字典项文本
     */
    @TableField(value = "name")
    private String name;

    /**
     * 字典项值
     */
    @TableField(value = "value")
    private String value;

    /**
     * 禁用状态(true1-禁用，false0-不禁用）
     */
    @TableField(value = "disabled")
    private Boolean disabled;

    /**
     * 排序
     */
    @TableField(value = "sort_no")
    private Double sortNo;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

}
