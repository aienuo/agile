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
 * 组织机构
 * </p>
 *
 * @author XinLau
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_organization")
public class Organization extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 父级编号
     */
    @TableField(value = "parent_id", updateStrategy = FieldStrategy.IGNORED)
    private Long parentId;

    /**
     * 组织机构名称
     */
    @TableField(value = "organization_name")
    private String organizationName;

    /**
     * 组织机构编号
     */
    @TableField(value = "organization_code")
    private String organizationCode;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 排序号
     */
    @TableField(value = "sort_order")
    private Double sortOrder;

    /**
     * 删除状态（0-正常，1-已删除）
     * 强制要求逻辑删除的注解字段要放在最后
     * @TableLogic(value = "0")
     */
    @TableField(value = "del_flag")
    private Integer delFlag;

}
