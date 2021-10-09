package com.imis.agile.module.online.model.vo;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.imis.agile.module.api.model.vo.ButtonVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * PageElementsVO<br>
 * 在线开发 - 页面生成元素
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年09月04日 14:40
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "在线开发 - 页面生成元素 - 返回值", description = "在线开发 - 页面生成元素 - 返回值")
public class PageElementsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "页面ID")
    private String id;

    /**
     * 页面名称
     */
    @ApiModelProperty(value = "页面名称")
    private String name;

    /**
     * 查询参数列表
     */
    @ApiModelProperty(value = "查询参数列表")
    private List<SearchElementVO> searchList;

    /**
     * 标题按钮列表
     */
    @ApiModelProperty(value = "标题按钮列表")
    private List<ButtonVO> headerButtonList;

    /**
     * 表格标题列表
     */
    @ApiModelProperty(value = "表格标题列表")
    private List<TableColumnElementVO> tableColumnList;

    /**
     * 表格按钮列表
     */
    @ApiModelProperty(value = "表格按钮列表")
    private List<ButtonVO> tableButtonList;

    /**
     * 支持分页查询
     */
    @ApiModelProperty(value = "支持分页查询")
    private Boolean paging;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private List<OrderItem> sortFieldList;

    /**
     * 新增表单样式（dialog、drawer）
     */
    @ApiModelProperty(value = "新增表单样式（dialog、drawer）")
    private String insertStyle;

    /**
     * 新增表单元素
     */
    @ApiModelProperty(value = "新增表单元素")
    private List<FormElementVO> insertColumnList;

    /**
     * 更新表单样式（dialog、drawer）
     */
    @ApiModelProperty(value = "更新表单样式（dialog、drawer）")
    private String updateStyle;

    /**
     * 更新表单元素
     */
    @ApiModelProperty(value = "更新表单元素")
    private List<FormElementVO> updateColumnList;

}
