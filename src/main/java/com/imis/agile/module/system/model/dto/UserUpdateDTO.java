package com.imis.agile.module.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * UserUpdateDTO<br>
 * 系统用户 - 用户更新参数
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月11日 09:18
 */
@Data
@Schema(title = "系统用户 - 用户更新参数", description = "系统用户 - 用户更新参数")
@EqualsAndHashCode(callSuper = false)
public class UserUpdateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @Schema(title = "用户编号", description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long id;

    /**
     * 用户名称
     */
    @Schema(title = "用户名称", description = "用户名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String realname;

    /**
     * 身份证号
     */
    @Schema(title = "身份证号", description = "身份证号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Pattern(regexp = "(^\\d{8}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}$)|(^\\d{6}(18|19|20)\\d{2}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}(\\d|X|x)$)")
    private String identityNumber;

    /**
     * 电子邮箱
     */
    @Schema(title = "电子邮箱", description = "电子邮箱")
    @Email
    private String email;

    /**
     * 手机号码
     */
    @Schema(title = "手机号码", description = "手机号码")
    @Pattern(regexp = "^(?:(?:\\+|00)86)?1(?:(?:3[\\d])|(?:4[5-7|9])|(?:5[0-3|5-9])|(?:6[5-7])|(?:7[0-8])|(?:8[\\d])|(?:9[1|8|9]))\\d{8}$")
    private String phone;

    /**
     * 头像
     */
    @Schema(title = "头像", description = "头像")
    private String avatar;

    /**
     * 角色列表
     */
    @Schema(title = "角色列表", description = "角色列表")
    private List<Long> roleList;

    /**
     * 组织机构列表
     */
    @Schema(title = "组织机构列表", description = "组织机构列表")
    private List<Long> organizationList;

}
