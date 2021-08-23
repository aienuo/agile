package com.imis.agile.constant.base;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * BaseTreeVO<br>
 * Tree 对象
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年07月23日 09:04
 */
@Data
@ApiModel(value = "Tree 对象", description = "Tree 对象")
@EqualsAndHashCode(callSuper = false)
public class BaseTreeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private String id;

    /**
     * 父级ID
     */
    @TableField(value = "父级ID")
    private String parentId;

    /**
     * 子级列表
     */
    @ApiModelProperty(value = "子级列表")
    private List<BaseTreeVO> children;


}
