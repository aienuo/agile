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
 * 在线开发 - 数据库表表索引
 * </p>
 *
 * @author XinLau
 * @since 2020-10-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("onl_table_index")
public class TableIndex extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表ID
     */
    @TableField(value = "table_head_id")
    private Long tableHeadId;

    /**
     * 索引名称
     */
    @TableField(value = "index_name")
    private String indexName;

    /**
     * 索引栏位
     */
    @TableField(value = "index_field")
    private String indexField;

    /**
     * 索引类型
     */
    @TableField(value = "index_type")
    private String indexType;

    /**
     * 备注
     */
    @TableField(value = "description")
    private String description;

    /**
     * 是否同步数据库（1未同步；0已同步）
     */
    @TableField(value = "synchronize")
    private Integer synchronize;

}
