package com.imis.agile.module.online.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.imis.agile.module.api.model.vo.ItemVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * SearchElementVO<br>
 * 在线开发 - 页面搜索元素
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年09月04日 14:58
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "在线开发 - 页面搜索元素 - 返回值", description = "在线开发 - 页面搜索元素 - 返回值")
public class SearchElementVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组件类型
     */
    @ApiModelProperty(value = "组件类型")
    private String type;

    /**
     * 组件绑定值（字段名称）
     */
    @ApiModelProperty(value = "组件绑定值（字段名称）")
    private String name;

    /**
     * 组件中文名称
     */
    @ApiModelProperty(value = "组件中文名称")
    private String textName;

    /**
     * 组件尺寸（medium / small / mini）
     */
    @ApiModelProperty(value = "组件尺寸（medium / small / mini）")
    private String size;

    /**
     * 最小数量
     */
    @ApiModelProperty(value = "最小数量")
    private Integer min;

    /**
     * 最大数量
     */
    @ApiModelProperty(value = "最大数量")
    private Integer max;

    /**
     * 计数器步长
     */
    @ApiModelProperty(value = "计数器步长")
    private Integer step;

    /**
     * 数值精度
     */
    @ApiModelProperty(value = "数值精度")
    private Integer precision;

    /**
     * 输入框占位文本
     */
    @ApiModelProperty(value = "输入框占位文本")
    private String placeholder;

    /**
     * 输入框头部图标
     */
    @ApiModelProperty(value = "输入框头部图标")
    private String prefixIcon;

    /**
     * 输入框尾部图标
     */
    @ApiModelProperty(value = "输入框尾部图标")
    private String suffixIcon;

    /**
     * 值选项列表
     */
    @ApiModelProperty(value = "值选项列表")
    private List<ItemVO> optionList;

}
