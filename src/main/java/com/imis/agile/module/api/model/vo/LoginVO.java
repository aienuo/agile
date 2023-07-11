package com.imis.agile.module.api.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
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
@Schema(title = "登录用户返回值", description = "登录用户返回值")
public class LoginVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Token
     */
    @Schema(title = "Token", description = "Token")
    private String token;

    /**
     * 系统用户
     */
    @Schema(title = "系统用户", description = "系统用户")
    private UserVO user;

}
