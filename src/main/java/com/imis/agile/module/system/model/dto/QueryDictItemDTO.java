package com.imis.agile.module.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
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
@Schema(title = "字典 - 值 - 分页查询参数", description = "字典 - 值 - 分页查询参数")
@EqualsAndHashCode(callSuper = false)
public class QueryDictItemDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典 - 项编号
     */
    @Schema(title = "字典 - 项编号", description = "字典 - 项编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long dictId;

    /**
     * ID
     */
    @Schema(title = "字典 - 值ID", description = "字典 - 值ID")
    private Long id;

    /**
     * 字典项文本
     */
    @Schema(title = "字典项文本", description = "字典项文本")
    private String name;

    /**
     * 禁用状态(true1-禁用，false0-不禁用）
     */
    @Schema(title = "禁用状态(true1-禁用，false0-不禁用）", description = "禁用状态(true1-禁用，false0-不禁用）")
    private Integer disabled;

}
