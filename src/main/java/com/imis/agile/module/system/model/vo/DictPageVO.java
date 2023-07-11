package com.imis.agile.module.system.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 字典 - 项
 * </p>
 *
 * @author XinLau
 * @since 2020-03-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "字典 - 项- 分页查询返回值", description = "字典 - 项- 分页查询返回值 对象")
public class DictPageVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(title = "字典 - 项ID", description = "字典 - 项ID")
    private String id;

    /**
     * 字典名称
     */
    @Schema(title = "字典名称", description = "字典名称")
    private String dictName;

    /**
     * 字典编码
     */
    @Schema(title = "字典编码", description = "字典编码")
    private String dictCode;

    /**
     * 字典类型（0-String，1-Number）
     */
    @Schema(title = "字典类型（0-String，1-Number）", description = "字典类型（0-String，1-Number）")
    private Integer dictType;

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

}
