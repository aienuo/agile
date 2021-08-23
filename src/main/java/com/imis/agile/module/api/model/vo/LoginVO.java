package com.imis.agile.module.api.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * LoginVO<br>
 * 登录用户返回值
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年12月23日 17:13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "登录用户返回值", description = "登录用户返回值")
public class LoginVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Token
     */
    @ApiModelProperty(value = "Token")
    private String token;

    /**
     * 系统用户
     */
    @ApiModelProperty(value = "系统用户")
    private UserVO user;

}
