package com.imis.agile.module.system.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * UserOrganizationDTO<br>
 * 系统用户组织机构关联 - 添加参数
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月11日 09:18
 */
@Data
@ApiModel(value = "系统用户组织机构关联 - 添加参数", description = "系统用户组织机构关联 - 用户添加参数")
@EqualsAndHashCode(callSuper = false)
public class UserOrganizationDTO {

    /**
     * 组织机构编号
     */
    @ApiModelProperty(value = "组织机构编号", required = true)
    @NotNull(message = "组织机构编号不能为空")
    private Long organizationId;

    /**
     * 是否负责部门（0-不负责，1-负责）
     */
    @TableField(value = "responsible")
    private Integer responsible;

}
