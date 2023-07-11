package com.imis.agile.module.system.model.dto;

import com.imis.agile.constant.base.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * PagingQueryDictDTO<br>
 * 字典 - 项 - 分页查询参数
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月11日 09:18
 */
@Data
@Schema(title = "字典 - 项 - 分页查询参数", description = "字典 - 项 - 分页查询参数")
@EqualsAndHashCode(callSuper = false)
public class PagingQueryDictDTO extends BasePageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(title = "字典 - 项ID", description = "字典 - 项ID")
    private Long id;

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

}
