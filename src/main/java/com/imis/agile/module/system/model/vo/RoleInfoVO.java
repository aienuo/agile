package com.imis.agile.module.system.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 系统角色 - 信息查询返回值
 * </p>
 *
 * @author: XinLau
 * @create: 2020-01-10 16:11
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "系统角色 - 信息查询返回值", description = "系统角色 - 信息查询返回值")
public class RoleInfoVO implements Serializable {

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

    /**
     * 菜单权限
     */
    @Schema(title = "菜单权限", description = "菜单权限")
    private List<String> menuList;

}
