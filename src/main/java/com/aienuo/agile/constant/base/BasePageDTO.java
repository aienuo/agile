package com.aienuo.agile.constant.base;

import com.aienuo.agile.constant.CommonConstant;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
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
@Schema(title = "分页条件", description = "分页条件")
@EqualsAndHashCode(callSuper = false)
public class BasePageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 页码
     */
    @Schema(title = "页码", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long pageNumber;

    public Long getPageNumber() {
        return this.pageNumber = pageNumber == null ? 1L : pageNumber;
    }

    /**
     * 页长
     */
    @Schema(title = "页长", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long pageSize;

    public Long getPageSize() {
        return this.pageSize = pageSize == null ? 10L : pageSize;
    }

    /**
     * 创建人
     */
    @Schema(title = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @Schema(title = "创建时间-起")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = CommonConstant.NORM_DATETIME_PATTERN)
    @DateTimeFormat(pattern = CommonConstant.NORM_DATETIME_PATTERN)
    private LocalDateTime createTimeStart;
    @Schema(title = "创建时间-止")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = CommonConstant.NORM_DATETIME_PATTERN)
    @DateTimeFormat(pattern = CommonConstant.NORM_DATETIME_PATTERN)
    private LocalDateTime createTimeEnd;

    /**
     * 更新人
     */
    @Schema(title = "更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @Schema(title = "更新时间-起")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = CommonConstant.NORM_DATETIME_PATTERN)
    @DateTimeFormat(pattern = CommonConstant.NORM_DATETIME_PATTERN)
    private LocalDateTime updateTimeStart;
    @Schema(title = "更新时间-止")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = CommonConstant.NORM_DATETIME_PATTERN)
    @DateTimeFormat(pattern = CommonConstant.NORM_DATETIME_PATTERN)
    private LocalDateTime updateTimeEnd;

    /**
     * 排序字段
     */
    @Schema(title = "排序字段")
    private List<OrderItem> sortFieldList;

    /**
     * 自定义查询条件（key -  条件字段名， value - 条件值）
     */
    @Schema(title = "自定义查询条件（key -  条件字段名， value - 条件值）")
    private Map<String, Object> condition;

    /**
     * 超级查询参数 这个参数方便自定义SQL条件查询（要考虑SQL注入，查询条件与数据权限冲突）
     */
    @Schema(title = "超级查询参数", hidden = true, accessMode = Schema.AccessMode.WRITE_ONLY)
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
