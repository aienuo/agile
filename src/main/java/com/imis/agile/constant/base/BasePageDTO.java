package com.imis.agile.constant.base;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.imis.agile.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * BasePageDTO<br>
 * 分页条件RO
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月11日 15:06
 */
@Data
@ApiModel(value = "分页条件", description = "分页条件")
@EqualsAndHashCode(callSuper = false)
public class BasePageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码", required = true)
    private Long pageNumber;

    public Long getPageNumber() {
        return this.pageNumber = pageNumber == null ? 1L : pageNumber;
    }

    /**
     * 页长
     */
    @ApiModelProperty(value = "页长", required = true)
    private Long pageSize;

    public Long getPageSize() {
        return this.pageSize = pageSize == null ? 10L : pageSize;
    }

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间-起")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = CommonConstant.NORM_DATETIME_PATTERN)
    @DateTimeFormat(pattern = CommonConstant.NORM_DATETIME_PATTERN)
    private LocalDateTime createTimeStart;
    @ApiModelProperty(value = "创建时间-止")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = CommonConstant.NORM_DATETIME_PATTERN)
    @DateTimeFormat(pattern = CommonConstant.NORM_DATETIME_PATTERN)
    private LocalDateTime createTimeEnd;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间-起")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = CommonConstant.NORM_DATETIME_PATTERN)
    @DateTimeFormat(pattern = CommonConstant.NORM_DATETIME_PATTERN)
    private LocalDateTime updateTimeStart;
    @ApiModelProperty(value = "更新时间-止")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = CommonConstant.NORM_DATETIME_PATTERN)
    @DateTimeFormat(pattern = CommonConstant.NORM_DATETIME_PATTERN)
    private LocalDateTime updateTimeEnd;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private List<OrderItem> sortFieldList;

    /**
     * 自定义查询条件（key -  条件字段名， value - 条件值）
     */
    @ApiModelProperty(value = "自定义查询条件（key -  条件字段名， value - 条件值）")
    private Map<String, Object> condition;

    /**
     * 超级查询参数 这个参数方便自定义SQL条件查询（要考虑SQL注入，查询条件与数据权限冲突）
     */
    @ApiModelProperty(value = "超级查询参数", hidden = true)
    private String superQueryParams;

    /**
     * 获取 自定义差查询条件的 值
     *
     * @param key - 条件字段名
     * @return Object - 条件值
     */
    public Object get(String key){
        return this.condition.get(key);
    }

    /**
     * 设置 自定义差查询条件
     *
     * @param key -  条件字段名
     * @param value - 条件值
     */
    public void set(String key, Object value){
        this.condition.put(key, value);
    }

    /**
     * 删除 自定义差查询条件
     *
     * @param key -  条件字段名
     */
    public void remove(String key){
        this.condition.remove(key);
    }

}
