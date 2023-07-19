package com.aienuo.agile.module.api.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * DictVO<br>
 * 字典项
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年08月18日 11:53
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "字典项 - 查询返回值", description = "字典项 - 查询返回值")
public class DictVO implements Serializable {

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
     * 字典值
     */
    @Schema(title = "字典值", description = "字典值")
    private List<ItemVO> itemList;

}
