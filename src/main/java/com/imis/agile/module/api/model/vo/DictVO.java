package com.imis.agile.module.api.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@ApiModel(value = "字典项 - 查询返回值", description = "字典项 - 查询返回值")
public class DictVO {

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

    /**
     * 字典类型（0-String，1-Number）
     */
    @ApiModelProperty(value = "字典类型（0-String，1-Number）")
    private Integer dictType;

    /**
     * 字典值
     */
    @ApiModelProperty(value = "字典值")
    private List<ItemVO> itemList;

}
