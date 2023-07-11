package com.imis.agile.module.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
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
@Schema(title = "系统角色 - 角色添加参数", description = "系统角色 - 角色添加参数")
@EqualsAndHashCode(callSuper = false)
public class RoleAddDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @Schema(title = "角色名称", description = "角色名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String roleName;

    /**
     * 角色编号
     */
    @Schema(hidden = true)
    private String roleCode;

    /**
     * 角色描述
     */
    @Schema(title = "角色描述", description = "角色描述")
    private String description;

    /**
     * 角色菜单权限关联
     */
    @Schema(title = "角色菜单权限关联", description = "角色菜单权限关联")
    private List<Long> menuList;

}
