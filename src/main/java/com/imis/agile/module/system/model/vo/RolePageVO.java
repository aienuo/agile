package com.imis.agile.module.system.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@ApiModel(value = "系统角色 - 分页查询返回值", description = "系统角色 - 分页查询返回值 对象")
public class RolePageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "角色ID")
    private String id;
    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    /**
     * 角色编号
     */
    @ApiModelProperty(value = "角色编号")
    private String roleCode;
    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    private String description;

}
