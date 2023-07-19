package com.aienuo.agile.module.system.model.entity;

import com.aienuo.agile.constant.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 富文本
 * </p>
 *
 * @author XinLau
 * @since 2020-07-24
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_rich_text")
public class RichText extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文本类型
     */
    @TableField(value = "text_type")
    private Integer textType;

    /**
     * 主表id
     */
    @TableField(value = "primary_table_id")
    private Long primaryTableId;

    /**
     * 文本内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 删除状态（0-正常，1-已删除）
     * 强制要求逻辑删除的注解字段要放在最后
     *
     * @TableLogic(value = "0")
     */
    @TableField(value = "del_flag")
    private Integer delFlag;

}
