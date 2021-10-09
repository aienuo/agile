package com.imis.agile.module.online.model.dto;

import com.imis.agile.constant.base.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * PagingTableBasicDTO<br>
 * 在线开发 - 表单基础信息
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年09月17日 10:22
 */
@Data
@ApiModel(value = "在线开发 - 表单基础信息 - 分页查询参数", description = "在线开发 - 表单基础信息 - 分页查询参数")
@EqualsAndHashCode(callSuper = false)
public class PagingTableBasicDTO extends BasePageDTO implements Serializable {

    /**
     * 表类型（0-单表，1-主表，2-附表）
     */
    @ApiModelProperty(value = "表类型（0-单表，1-主表，2-附表）")
    private Integer tableType;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String textName;
    /**
     * 表名
     */
    @ApiModelProperty(value = "表名")
    private String tableName;
    /**
     * 同步状态（0-已同步，1-未同步）
     */
    @ApiModelProperty(value = "同步状态（0-已同步，1-未同步）")
    private Integer synchronize;

}
