package com.aienuo.agile.module.api.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 角色管理 - 登录 - 返回值
 * </p>
 *
 * @author XinLau
 * @since 2020-03-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "角色管理 - 登录 - 返回值", description = "角色管理 - 登录 - 返回值")
public class RoleVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(title = "角色ID", description = "角色ID")
    private String id;

    /**
     * 角色名称
     */
    @Schema(title = "角色名称", description = "角色名称")
    private String roleName;

    /**
     * 角色编码
     */
    @Schema(title = "角色编码", description = "角色编码")
    private String roleCode;

    /**
     * 描述
     */
    @Schema(title = "描述", description = "描述")
    private String description;

}
