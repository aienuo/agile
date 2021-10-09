package com.imis.agile.module.online.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * TableAddDTO<br>
 * 在线开发 - 表单信息 - 更新
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年09月18日 14:01
 */
@Data
@ApiModel(value = "在线开发 - 表单信息 - 更新对象参数", description = "在线开发 - 表单信息 - 更新对象参数")
@EqualsAndHashCode(callSuper = false)
public class TableUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "表单信息编号不能为空")
    private Long id;

    /**
     * 表类型（0-单表，1-主表，2-附表）
     */
    @ApiModelProperty(value = "表类型（0-单表，1-主表，2-附表）", required = true)
    @NotNull(message = "请选择表类型")
    private Integer tableType;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", required = true)
    @NotBlank(message = "请输入名称")
    private String textName;
    /**
     * 表名
     */
    @ApiModelProperty(value = "表名", required = true)
    @NotBlank(message = "请输入表名")
    private String tableName;
    /**
     * 是否分页（0-否 1-是）
     */
    @ApiModelProperty(value = "是否分页（0否 1是）")
    @NotNull(message = "请选择是否分页")
    private Integer paging;
    /**
     * 新增表单风格（dialog-弹框、drawer-抽屉）
     */
    @ApiModelProperty(value = "新增表单风格（dialog-弹框、drawer-抽屉）")
    @NotBlank(message = "请选择新增表单风格")
    private String insertStyle;
    /**
     * 更新表单风格（dialog-弹框、drawer-抽屉）
     */
    @ApiModelProperty(value = "更新表单风格（dialog-弹框、drawer-抽屉）")
    @NotBlank(message = "请选择更新表单风格")
    private String updateStyle;
    /**
     * 主题模板
     */
    @ApiModelProperty(value = "主题模板")
    private String themeTemplate;
    /**
     * 表描述
     */
    @ApiModelProperty(value = "表描述")
    private String description;
    /**
     * 表字段
     */
    @ApiModelProperty(value = "表字段")
    private List<FieldAddDTO> fieldData;
    @ApiModelProperty(value = "被删除的表字段")
    private List<Long> fieldId;
    /**
     * 表索引
     */
    @ApiModelProperty(value = "表索引")
    private List<IndexAddDTO> indexData;
    @ApiModelProperty(value = "被删除的表索引")
    private List<Long> indexId;

}
