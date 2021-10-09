package com.imis.agile.module.system.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.imis.agile.constant.base.BaseTreeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * OrganizationTreeInfoVO<br>
 * 组织机构 - 树查询 - 返回值
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年08月24日 15:18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "组织机构 - 树查询 - 返回值", description = "组织机构 - 树查询 - 返回值")
public class OrganizationTreeInfoVO extends BaseTreeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组织机构名称
     */
    @ApiModelProperty(value = "组织机构名称")
    private String label;

    /**
     * 排序号
     */
    @ApiModelProperty(value = "排序号")
    private Double sortNo;

    /**
     * 冻结状态(0-正常，1-冻结）
     */
    @ApiModelProperty(value = "冻结状态(false0-正常，true1-冻结）")
    private Boolean disabled;

}
