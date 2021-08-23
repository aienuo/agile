package com.imis.agile.module.system.model.dto;

import com.imis.agile.constant.base.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value="字典 - 项 - 分页查询参数", description="字典 - 项 - 分页查询参数")
@EqualsAndHashCode(callSuper = false)
public class PagingQueryDictDTO extends BasePageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "字典 - 项ID")
    private String id;

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称")
    private String dictName;

    /**
     * 字典编码
     */
    @ApiModelProperty(value = "字典编码")
    private String dictCode;

}
