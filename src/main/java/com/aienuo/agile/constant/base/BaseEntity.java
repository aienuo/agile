package com.aienuo.agile.constant.base;

import com.aienuo.agile.constant.CommonConstant;
import com.aienuo.agile.constant.DataBaseConstant;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * Po基类
 * </p>
 *
 * @author XinLau
 * @since 2020-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public abstract class BaseEntity<T extends Model<T>> extends Model<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = DataBaseConstant.P_KEY_FIELD, type = IdType.ASSIGN_ID)
    @Schema(description = "ID")
    private Long id;
    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @TableField(value = DataBaseConstant.CREATE_BY_FIELD, fill = FieldFill.INSERT)
    private String createBy;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = CommonConstant.NORM_DATETIME_PATTERN)
    @DateTimeFormat(pattern = CommonConstant.NORM_DATETIME_PATTERN)
    @TableField(value = DataBaseConstant.CREATE_TIME_FIELD, fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 更新人
     */
    @Schema(description = "更新人")
    @TableField(value = DataBaseConstant.UPDATE_BY_FIELD, fill = FieldFill.UPDATE)
    private String updateBy;
    /**
     * 更新时间（乐观锁）
     */
    @Version
    @Schema(description = "更新时间（乐观锁）")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = CommonConstant.NORM_DATETIME_PATTERN)
    @DateTimeFormat(pattern = CommonConstant.NORM_DATETIME_PATTERN)
    @TableField(value = DataBaseConstant.UPDATE_TIME_FIELD, fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
