package com.aienuo.agile.module.system.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 系统角色 - 分页查询返回值
 * </p>
 *
 * @author: XinLau
 * @create: 2020-01-10 16:11
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "系统角色 - 分页查询返回值", description = "系统角色 - 分页查询返回值 对象")
public class RolePageVO implements Serializable {

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
     * 角色编号
     */
    @Schema(title = "角色编号", description = "角色编号")
    private String roleCode;
    /**
     * 角色描述
     */
    @Schema(title = "角色描述", description = "角色描述")
    private String description;

}
