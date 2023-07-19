package com.aienuo.agile.module.system.model.vo;

import com.aienuo.agile.constant.base.BaseTreeVO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 字典 - 值
 * </p>
 *
 * @author XinLau
 * @since 2020-03-24
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "字典 - 值 - 查询返回值", description = "字典 - 值 - 查询返回值")
public class DictItemVO extends BaseTreeVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典ID
     */
    @Schema(title = "字典 - 项编号", description = "字典 - 项编号")
    private String dictId;

    /**
     * 字典项文本
     */
    @Schema(title = "字典项文本", description = "字典项文本")
    private String name;

    /**
     * 字典项值
     */
    @Schema(title = "字典项值", description = "字典项值")
    private String value;

    /**
     * 禁用状态(true1-禁用，false0-不禁用）
     */
    @Schema(title = "禁用状态(true1-禁用，false0-不禁用）", description = "禁用状态(true1-禁用，false0-不禁用）")
    private Boolean disabled;

    /**
     * 排序
     */
    @Schema(title = "排序", description = "排序")
    private Double sortNo;

    /**
     * 描述
     */
    @Schema(title = "描述", description = "描述")
    private String description;

}
