package com.aienuo.agile.module.api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户登录表单对象 DTO
 *
 * @author XinLau
 * @since 2020-03-17
 */
@Data
@Schema(title = "登录对象", description = "登录对象")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "账号", description = "账号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String username;

    @Schema(title = "密码", description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String password;

    @Schema(title = "验证码", description = "验证码")
    private String captcha;

    @Schema(title = "验证码标识", description = "验证码标识")
    private String verificationCodeIdentification;

}
