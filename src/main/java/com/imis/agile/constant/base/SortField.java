package com.imis.agile.constant.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * SortField<br>
 * 排序字段
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年12月25日 15:53
 */
@Data
@ApiModel(value = "排序字段", description = "排序字段")
@EqualsAndHashCode(callSuper = false)
public class SortField implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 需要进行排序的字段名称
     */
    @ApiModelProperty(value = "需要进行排序的字段名称")
    private String fieldsName;

    /**
     * 是否正序排列，默认 true
     */
    @ApiModelProperty(value = "需要进行排序的字段名称")
    private Boolean asc = Boolean.TRUE;

}
