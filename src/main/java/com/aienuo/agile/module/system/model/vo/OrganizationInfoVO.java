package com.aienuo.agile.module.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * OrganizationInfoVO<br>
 * 组织机构详细信息 - 查询返回值
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年08月24日 15:33
 */
@Data
@Schema(title = "组织机构详细信息 - 查询返回值", description = "组织机构详细信息 - 查询返回值")
@EqualsAndHashCode(callSuper = false)
public class OrganizationInfoVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 组织机构编号
     */
    @Schema(title = "组织机构编号", description = "组织机构编号")
    private String id;

    /**
     * 父记录id
     */
    @Schema(title = "父记录Id", description = "父记录Id")
    private String parentId;

    /**
     * 组织机构名称
     */
    @Schema(title = "组织机构名称", description = "组织机构名称")
    private String organizationName;

    /**
     * 排序号
     */
    @Schema(title = "排序号", description = "排序号")
    private Double sortNo;

    /**
     * 冻结状态(0-正常，1-冻结）
     */
    @Schema(title = "冻结状态(0-正常，1-冻结）", description = "冻结状态(0-正常，1-冻结）")
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
    private String organizationUserId;

}
