package com.imis.agile.module.system.model.dto;

import com.imis.agile.constant.base.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * PagingQueryRoleDTO<br>
 * 系统角色 - 分页查询参数
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月11日 09:18
 */
@Data
@ApiModel(value = "系统角色 - 分页查询参数", description = "系统角色 - 分页查询参数")
@EqualsAndHashCode(callSuper = false)
public class PagingQueryRoleDTO extends BasePageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
    @ApiModelProperty(value = "角色编号")
    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

}
