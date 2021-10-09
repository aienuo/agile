package com.imis.agile.module.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
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
@ApiModel(value = "系统用户 - 用户更新参数", description = "系统用户 - 用户更新参数")
@EqualsAndHashCode(callSuper = false)
public class UserUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号", required = true)
    @NotNull(message = "用户编号不能为空")
    private Long id;

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称", required = true)
    @NotBlank(message = "用户名称不能为空")
    private String realname;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号", required = true)
    @NotBlank(message = "身份证号不能为空")
    @Pattern(regexp = "(^\\d{8}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}$)|(^\\d{6}(18|19|20)\\d{2}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}(\\d|X|x)$)", message = "身份证号格式错误")
    private String identityNumber;

    /**
     * 出生日期
     */
    @ApiModelProperty(hidden = true)
    private LocalDate birthday;
    /**
     * 性别(2-默认未知，1-男，0-女)
     */
    @ApiModelProperty(hidden = true)
    private Integer sex;

    /**
     * 电子邮箱
     */
    @ApiModelProperty(value = "电子邮箱")
    @Email(message = "电子邮箱格式错误")
    private String email;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    @Pattern(regexp = "^(?:(?:\\+|00)86)?1(?:(?:3[\\d])|(?:4[5-7|9])|(?:5[0-3|5-9])|(?:6[5-7])|(?:7[0-8])|(?:8[\\d])|(?:9[1|8|9]))\\d{8}$", message = "手机号码格式错误")
    private String phone;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 角色列表
     */
    @ApiModelProperty(value = "角色列表")
    private List<Long> roleList;

}
