package com.imis.agile.module.system.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@ApiModel(value = "字典 - 项- 分页查询返回值", description = "字典 - 项- 分页查询返回值 对象")
public class DictPageVO implements Serializable {

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

    /**
     * 字典类型（0-String，1-Number）
     */
    @ApiModelProperty(value = "字典类型（0-String，1-Number）")
    private Integer dictType;

    /**
     * 冻结状态(0-正常，1-冻结）
     */
    @ApiModelProperty(value = "冻结状态(0-正常，1-冻结）")
    private Integer status;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

}
