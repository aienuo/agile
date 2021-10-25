package com.imis.agile.module.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * OrganizationUpdateDTO<br>
 * 组织机构 - 节点编辑参数
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年08月24日 15:03
 */
@Data
@ApiModel(value = "组织机构 - 节点编辑参数", description = "组织机构 - 节点编辑参数")
@EqualsAndHashCode(callSuper = false)
public class OrganizationEditDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组织机构编号
     */
    @ApiModelProperty(value = "组织机构编号", required = true)
    @NotNull
    private Long id;

    /**s
     * 父记录id
     */
    @ApiModelProperty(value = "父记录Id")
    private Long parentId;

    /**
     * 排序号
     */
    @ApiModelProperty(value = "排序号排序号", required = true)
    @NotNull
    private Double sortNo;

}
