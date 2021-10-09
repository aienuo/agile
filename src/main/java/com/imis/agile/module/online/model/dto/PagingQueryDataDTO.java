package com.imis.agile.module.online.model.dto;

import com.imis.agile.constant.base.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * PagingQueryDataDTO<br>
 * 在线开发 - 页面列表数据 - 分页查询参数
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年10月09日 14:02
 */
@Data
@ApiModel(value = "在线开发 - 页面列表数据 - 分页查询参数", description = "在线开发 - 页面列表数据 - 分页查询参数")
@EqualsAndHashCode(callSuper = false)
public class PagingQueryDataDTO extends BasePageDTO implements Serializable {

    /**
     * 查询条件对象
     */
    @ApiModelProperty(value = "查询条件对象")
    private String queryConditions;

    /**
     * 是否分页
     */
    @ApiModelProperty(value = "是否分页")
    private Boolean paging;

}
