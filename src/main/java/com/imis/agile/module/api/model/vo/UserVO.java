package com.imis.agile.module.api.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.imis.agile.constant.CommonConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 系统用户 - 登录 - 返回值
 * </p>
 *
 * @author XinLau
 * @since 2020-03-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "系统用户 - 登录 - 返回值", description = "系统用户 - 登录 - 返回值")
public class UserVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 登录账号
     */
    @Schema(title = "登录账号", description = "登录账号")
    private String username;

    /**
     * 头像
     */
    @Schema(title = "头像", description = "头像")
    private String avatar;

    /**
     * 真实姓名
     */
    @Schema(title = "真实姓名", description = "真实姓名")
    private String realname;

    /**
     * 身份证号
     */
    @Schema(title = "身份证号", description = "身份证号")
    private String identityNumber;

    /**
     * 电子邮箱
     */
    @Schema(title = "电子邮箱", description = "电子邮箱")
    private String email;

    /**
     * 手机号码
     */
    @Schema(title = "手机号码", description = "手机号码")
    private String phone;

    /**
     * 出生日期
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = CommonConstant.NORM_DATE_PATTERN)
    @DateTimeFormat(pattern = CommonConstant.NORM_DATE_PATTERN)
    @Schema(title = "出生日期", description = "出生日期")
    private LocalDate birthday;

    /**
     * 性别(2-默认未知，1-男，0-女)
     */
    @Schema(title = "性别(2-默认未知，1-男，0-女)", description = "性别(2-默认未知，1-男，0-女)")
    private Integer sex;

    /**
     * 年龄
     */
    @Schema(title = "年龄", description = "年龄")
    private Integer age;

}
