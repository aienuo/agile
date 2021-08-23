package com.imis.agile.module.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.imis.agile.constant.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 字典 - 项
 * </p>
 *
 * @author XinLau
 * @since 2020-03-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dict")
public class Dict extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典名称
     */
    @TableField(value = "dict_name")
    private String dictName;

    /**
     * 字典编码
     */
    @TableField(value = "dict_code")
    private String dictCode;

    /**
     * 字典类型（0-String，1-Number）
     */
    @TableField(value = "dict_type")
    private Integer dictType;

    /**
     * 冻结状态(0-正常，1-冻结）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

}
