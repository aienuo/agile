package com.imis.agile.module.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * RoleUpdateDTO<br>
 * 系统角色 - 角色更新参数
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月11日 09:18
 */
@Data
@ApiModel(value = "系统角色 - 角色更新参数", description = "系统角色 - 角色更新参数")
@EqualsAndHashCode(callSuper = false)
public class RoleUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
    @ApiModelProperty(value = "角色编号", required = true)
    @NotNull(message = "角色编号不能为空")
    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

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
