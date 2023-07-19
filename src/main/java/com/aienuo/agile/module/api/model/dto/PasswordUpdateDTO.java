package com.aienuo.agile.module.api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
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
@Schema(title = "自助密码修改对象", description = "自助密码修改对象")
public class PasswordUpdateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "登录账号", description = "登录账号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String username;

    @Schema(title = "旧密码", description = "旧密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String oldPassword;

    @Schema(title = "新密码", description = "新密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String newPassword;

    @Schema(title = "验证码", description = "验证码")
    private String captcha;

    @Schema(title = "验证码标识", description = "验证码标识")
    private String verificationCodeIdentification;

}
