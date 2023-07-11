package com.imis.agile.module.api.model.vo;

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
 * 字典 - 简化版值
 * </p>
 *
 * @author XinLau
 * @since 2020-03-24
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "简化版字典值 - 查询返回值", description = "简化版字典值 - 查询返回值")
public class ItemVO extends BaseTreeVO implements Serializable {

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
     * 字典项文本
     *
     * @return 字典项文本
     */
    public String getTitle() {
        return this.name;
    }

    /**
     * 字典项文本
     *
     * @return 字典项文本
     */
    public String getText() {
        return this.name;
    }

}
