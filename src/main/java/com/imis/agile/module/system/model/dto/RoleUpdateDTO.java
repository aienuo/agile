package com.imis.agile.module.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
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
@Schema(title = "系统角色 - 角色更新参数", description = "系统角色 - 角色更新参数")
@EqualsAndHashCode(callSuper = false)
public class RoleUpdateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
    @Schema(title = "角色编号", description = "角色编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long id;

    /**
     * 角色名称
     */
    @Schema(title = "角色名称", description = "角色名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String roleName;

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
