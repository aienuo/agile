package com.imis.agile.module.system.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@ApiModel(value = "组织机构下的用户 - 查询返回值", description = "组织机构下的用户 - 查询返回值")
public class OrganizationUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private String id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "系统用户编号")
    private String userId;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realname;

    /**
     * 组织机构id
     */
    @ApiModelProperty(value = "组织机构编号")
    private String organizationId;

    /**
     * 是否负责部门（0-不负责，1-负责）
     */
    @ApiModelProperty(value = "是否负责部门（0-不负责，1-负责）")
    private Integer responsible;

}
