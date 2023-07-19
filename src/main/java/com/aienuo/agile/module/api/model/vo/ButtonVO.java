package com.aienuo.agile.module.api.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 按钮权限 - 登录 - 返回值
 * </p>
 *
 * @author XinLau
 * @since 2020-03-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "按钮权限 - 登录 - 返回值", description = "按钮权限 - 登录 - 返回值")
public class ButtonVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(title = "按钮ID", description = "按钮ID")
    private String id;

    /**
     * 按钮类型（primary / success / warning / danger / info / text）
     */
    @Schema(title = "按钮类型（primary / success / warning / danger / info / text）", description = "按钮类型（primary / success / warning / danger / info / text）")
    private String type;

    /**
     * 按钮名称（textName）
     */
    @Schema(title = "按钮名称（textName）", description = "按钮名称（textName）")
    private String name;

    /**
     * 按钮尺寸（medium / small / mini）
     */
    @Schema(title = "组件尺寸（medium / small / mini）", description = "组件尺寸（medium / small / mini）")
    private String size;

    /**
     * 按钮（name）
     */
    @Schema(title = "按钮（name）", description = "按钮（name）")
    private String url;

    /**
     * 按钮图标
     */
    @Schema(title = "按钮图标", description = "按钮图标")
    private String icon;

}
