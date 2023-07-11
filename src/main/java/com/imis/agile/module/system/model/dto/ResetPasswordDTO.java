package com.imis.agile.module.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
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
@Schema(title = "系统用户 - 密码重置参数", description = "系统用户 - 密码重置参数")
@EqualsAndHashCode(callSuper = false)
public class ResetPasswordDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "用户编号", description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long userId;

    @Schema(title = "新密码", description = "新密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String newPassword;

    @Schema(title = "验证码", description = "验证码")
    private String captcha;

    @Schema(title = "验证码标识", description = "验证码标识")
    private String verificationCodeIdentification;

}
