package com.imis.agile.module.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * QueryDictItemDTO<br>
 * 字典 - 值 - 分页查询参数
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月11日 09:18
 */
@Data
@ApiModel(value = "字典 - 值 - 分页查询参数", description = "字典 - 值 - 分页查询参数")
@EqualsAndHashCode(callSuper = false)
public class QueryDictItemDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "字典 - 值ID")
    private Long id;

    /**
     * 字典 - 项编号
     */
    @ApiModelProperty(value = "字典 - 项编号", required = true)
    @NotNull(message = "字典 - 项编号不能为空")
    private Long dictId;

    /**
     * 字典项文本
     */
    @ApiModelProperty(value = "字典项文本")
    private String name;

    /**
     * 禁用状态(true1-禁用，false0-不禁用）
     */
    @ApiModelProperty(value = "禁用状态(true1-禁用，false0-不禁用）")
    private Integer disabled;

}
