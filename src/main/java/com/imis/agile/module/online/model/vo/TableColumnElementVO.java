package com.imis.agile.module.online.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * TableColumnElementVO<br>
 * 在线开发 - 页面列表元素
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年09月04日 16:43
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "在线开发 - 页面列表元素 - 返回值", description = "在线开发 - 页面列表元素 - 返回值")
public class TableColumnElementVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段标题
     */
    @ApiModelProperty(value = "字段标题")
    private String name;

    /**
     * 字段名
     */
    @ApiModelProperty(value = "字段名")
    private String prop;

    /**
     * 宽度
     */
    @ApiModelProperty(value = "宽度")
    private String width;

    /**
     * 最小宽度
     */
    @ApiModelProperty(value = "最小宽度")
    private String minWidth;

    /**
     * 对齐方式（left/center/right）
     */
    @ApiModelProperty(value = "对齐方式（left/center/right）")
    private String align;

}
