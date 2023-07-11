package com.imis.agile.module.system.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.imis.agile.constant.base.BaseTreeVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
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
@Schema(title = "组织机构 - 树查询 - 返回值", description = "组织机构 - 树查询 - 返回值")
public class OrganizationTreeInfoVO extends BaseTreeVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 组织机构名称
     */
    @Schema(title = "组织机构名称", description = "组织机构名称")
    private String label;

    /**
     * 排序号
     */
    @Schema(title = "排序号", description = "排序号")
    private Double sortNo;

    /**
     * 冻结状态(0-正常，1-冻结）
     */
    @Schema(title = "冻结状态(false0-正常，true1-冻结）", description = "冻结状态(false0-正常，true1-冻结）")
    private Boolean disabled;

}
