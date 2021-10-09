package com.imis.agile.module.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * RoleAddDTO<br>
 * 系统角色 - 角色添加参数
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月11日 09:18
 */
@Data
@ApiModel(value = "系统角色 - 角色添加参数", description = "系统角色 - 角色添加参数")
@EqualsAndHashCode(callSuper = false)
public class RoleAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空")
    private String roleName;
    /**
     * 角色编号
     */
    @ApiModelProperty(hidden = true)
    private String roleCode;
    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    private String description;

    /**
     * 角色菜单权限关联
     */
    @ApiModelProperty(value = "角色菜单权限关联")
    private List<Long> menuList;

}
