package com.aienuo.agile.module.system.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 组织机构下的用户 - 查询返回值
 * </p>
 *
 * @author: XinLau
 * @create: 2020-01-10 16:11
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "组织机构下的用户 - 查询返回值", description = "组织机构下的用户 - 查询返回值")
public class OrganizationUserVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Schema(title = "编号", description = "编号")
    private String id;

    /**
     * 用户id
     */
    @Schema(title = "系统用户编号", description = "系统用户编号")
    private String userId;

    /**
     * 真实姓名
     */
    @Schema(title = "真实姓名", description = "真实姓名")
    private String realname;

    /**
     * 组织机构id
     */
    @Schema(title = "组织机构编号", description = "组织机构编号")
    private String organizationId;

    /**
     * 是否负责部门（0-不负责，1-负责）
     */
    @Schema(title = "是否负责部门（0-不负责，1-负责）", description = "是否负责部门（0-不负责，1-负责）")
    private Integer responsible;

}
