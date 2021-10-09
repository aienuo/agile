package com.imis.agile.module.online.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * IndexAddDTO<br>
 * 在线开发 - 数据库索引 - 新增
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年09月18日 16:01
 */
@Data
@ApiModel(value = "在线开发 - 数据库索引 - 新增对象参数", description = "在线开发 - 数据库索引 - 新增对象参数")
@EqualsAndHashCode(callSuper = false)
public class IndexAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 索引名称
     */
    @ApiModelProperty(value = "索引名称")
    private String indexName;

    /**
     * 索引栏位
     */
    @ApiModelProperty(value = "索引栏位")
    private String indexField;

    /**
     * 索引类型
     */
    @ApiModelProperty(value = "索引类型")
    private String indexType;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String description;
    
}
