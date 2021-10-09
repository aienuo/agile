package com.imis.agile.module.online.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.imis.agile.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * 在线开发 - 表单基础信息
 *
 * @author XinLau
 * @since 2020-10-03
*/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "在线开发 - 表单基础信息 - 返回值", description = "在线开发 - 表单基础信息 - 返回值")
public class TableBasicPageVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 表类型（0-单表，1-主表，2-附表）
     */
    @ApiModelProperty(value = "表类型（0-单表，1-主表，2-附表）")
    private Integer tableType;
    /**
     * 表版本
     */
    @ApiModelProperty(value = "表版本")
    private Integer tableVersion;
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
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = CommonConstant.NORM_DATETIME_PATTERN)
    @DateTimeFormat(pattern = CommonConstant.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = CommonConstant.NORM_DATETIME_PATTERN)
    @DateTimeFormat(pattern = CommonConstant.NORM_DATETIME_PATTERN)
    private LocalDateTime updateTime;

}
