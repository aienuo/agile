package com.imis.agile.module.system.model.entity;

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
 * 用户组织机构关联
 * </p>
 *
 * @author XinLau
 * @since 2020-03-20
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_organization")
public class UserOrganization extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 组织机构id
     */
    @TableField(value = "organization_id")
    private Long organizationId;

    /**
     * 是否负责部门（0-不负责，1-负责）
     */
    @TableField(value = "responsible")
    private Integer responsible;

}
