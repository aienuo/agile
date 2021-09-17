package com.imis.agile.module.api.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录表单对象 DTO
 *
 * @author XinLau
 * @since 2020-03-17
 */
@Data
@ApiModel(value = "登录对象", description = "登录对象")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginDTO {

    @ApiModelProperty(value = "账号", required = true)
    @NotBlank(message = "帐号输入不正确")
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码错误")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String captcha;

    @ApiModelProperty(value = "验证码标识")
    private String verificationCodeIdentification;

}