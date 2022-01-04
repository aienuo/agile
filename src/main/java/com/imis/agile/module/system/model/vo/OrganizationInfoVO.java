package com.imis.agile.module.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value = "组织机构详细信息 - 查询返回值", description = "组织机构详细信息 - 查询返回值")
@EqualsAndHashCode(callSuper = false)
public class OrganizationInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组织机构编号
     */
    @ApiModelProperty(value = "组织机构编号")
    private String id;

    /**
     * 父记录id
     */
    @ApiModelProperty(value = "父记录Id")
    private String parentId;

    /**
     * 组织机构名称
     */
    @ApiModelProperty(value = "组织机构名称")
    private String organizationName;

    /**
     * 排序号
     */
    @ApiModelProperty(value = "排序号")
    private Double sortNo;

    /**
     * 冻结状态(0-正常，1-冻结）
     */
    @ApiModelProperty(value = "冻结状态(0-正常，1-冻结）")
    private Integer status;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 用户组织机构关联
     */
    @ApiModelProperty(value = "用户组织机构关联编号")
    private String organizationUserId;

}
