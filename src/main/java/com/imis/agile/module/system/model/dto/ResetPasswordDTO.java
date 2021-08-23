package com.imis.agile.module.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * ResetPasswordDTO<br>
 * 系统用户 - 密码重置参数
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月26日 14:23
 */
@Data
@ApiModel(value = "系统用户 - 密码重置参数", description = "系统用户 - 密码重置参数")
@EqualsAndHashCode(callSuper = false)
public class ResetPasswordDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户编号", required = true)
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    @ApiModelProperty(value = "新密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String newPassword;

    @ApiModelProperty(value = "验证码")
    private String captcha;

    @ApiModelProperty(value = "验证码标识")
    private String verificationCodeIdentification;

}
