package com.imis.agile.constant.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
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
@Schema(title = "Tree 对象", description = "Tree 对象")
@EqualsAndHashCode(callSuper = false)
public class BaseTreeVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(title = "ID")
    private String id;

    /**
     * 父级ID
     */
    @Schema(title = "父级ID")
    private String parentId;

    /**
     * 子级列表
     */
    @Schema(title = "子级列表")
    private List<BaseTreeVO> children;


}
