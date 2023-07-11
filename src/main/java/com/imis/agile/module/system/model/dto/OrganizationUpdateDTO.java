package com.imis.agile.module.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * OrganizationUpdateDTO<br>
 * 组织机构 - 修改参数
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年08月24日 15:03
 */
@Data
@Schema(title = "组织机构 - 修改参数", description = "组织机构 - 修改参数")
@EqualsAndHashCode(callSuper = false)
public class OrganizationUpdateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 组织机构编号
     */
    @Schema(title = "组织机构编号", description = "组织机构编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long id;

    /**
     * 父记录id
     */
    @Schema(title = "父记录Id", description = "父记录Id")
    private Long parentId;

    /**
     * 组织机构名称
     */
    @Schema(title = "组织机构名称", description = "组织机构名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String organizationName;

    /**
     * 排序号
     */
    @Schema(title = "排序号", description = "排序号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Double sortNo;

    /**
     * 冻结状态(0-正常，1-冻结）
     */
    @Schema(title = "冻结状态(0-正常，1-冻结）", description = "冻结状态(0-正常，1-冻结）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Integer status;

    /**
     * 描述
     */
    @Schema(title = "描述", description = "描述")
    private String description;

    /**
     * 用户组织机构关联
     */
    @Schema(title = "用户组织机构关联编号", description = "用户组织机构关联编号")
    private Long organizationUserId;

}
