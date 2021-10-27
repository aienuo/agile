package com.imis.agile.module.api.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * PasswordUpdateDTO<br>
 * 自助密码修改对象
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月26日 14:23
 */
@Data
@ApiModel(value = "自助密码修改对象", description = "自助密码修改对象")
public class PasswordUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登录账号", required = true)
    @NotBlank
    private String username;

    @ApiModelProperty(value = "旧密码", required = true)
    @NotBlank
    private String oldPassword;

    @ApiModelProperty(value = "新密码", required = true)
    @NotBlank
    private String newPassword;

    @ApiModelProperty(value = "验证码")
    private String captcha;

    @ApiModelProperty(value = "验证码标识")
    private String verificationCodeIdentification;

}
