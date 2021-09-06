package com.imis.agile.module.api.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 按钮权限 - 登录 - 返回值
 * </p>
 *
 * @author XinLau
 * @since 2020-03-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "按钮权限 - 登录 - 返回值", description = "按钮权限 - 登录 - 返回值")
public class ButtonVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "按钮ID")
    private String id;

    /**
     * 按钮类型（primary / success / warning / danger / info / text）
     */
    @ApiModelProperty(value = "按钮类型（primary / success / warning / danger / info / text）")
    private String type;

    /**
     * 按钮名称（textName）
     */
    @ApiModelProperty(value = "按钮名称（textName）")
    private String name;

    /**
     * 按钮尺寸（medium / small / mini）
     */
    @ApiModelProperty(value = "组件尺寸（medium / small / mini）")
    private String size;

    /**
     * 按钮（name）
     */
    @ApiModelProperty(value = "按钮（name）")
    private String url;

    /**
     * 按钮图标
     */
    @ApiModelProperty(value = "按钮图标")
    private String icon;

}
