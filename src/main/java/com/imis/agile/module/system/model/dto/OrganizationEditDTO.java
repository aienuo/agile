package com.imis.agile.module.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
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
@Schema(title = "组织机构 - 节点编辑参数", description = "组织机构 - 节点编辑参数")
@EqualsAndHashCode(callSuper = false)
public class OrganizationEditDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 组织机构编号
     */
    @Schema(title = "组织机构编号", description = "组织机构编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long id;

    /**s
     * 父记录id
     */
    @Schema(title = "父记录Id", description = "父记录Id")
    private Long parentId;

    /**
     * 排序号
     */
    @Schema(title = "排序号排序号", description = "排序号排序号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Double sortNo;

}
