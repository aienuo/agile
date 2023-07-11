package com.imis.agile.module.system.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.imis.agile.constant.CommonConstant;
import com.imis.agile.constant.base.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * PagingQueryUserDTO<br>
 * 系统用户 - 分页查询参数
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月11日 09:18
 */
@Data
@Schema(title = "系统用户 - 分页查询参数", description = "系统用户 - 分页查询参数")
@EqualsAndHashCode(callSuper = false)
public class PagingQueryUserDTO extends BasePageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @Schema(title = "用户编号", description = "用户编号")
    private Long id;

    /**
     * 登录账号
     */
    @Schema(title = "登录账号", description = "登录账号")
    private String username;

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
     * 出生日期
     */
    @Schema(title = "出生日期-始", description = "出生日期-始")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = CommonConstant.NORM_DATE_PATTERN)
    @DateTimeFormat(pattern = CommonConstant.NORM_DATE_PATTERN)
    private LocalDate birthday;
    @Schema(title = "出生日期-终", description = "出生日期-终")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = CommonConstant.NORM_DATE_PATTERN)
    @DateTimeFormat(pattern = CommonConstant.NORM_DATE_PATTERN)
    private LocalDate birthdayEnd;

    /**
     * 性别(2-默认未知，1-男，0-女)
     */
    @Schema(title = "性别(2-默认未知，1-男，0-女)", description = "性别(2-默认未知，1-男，0-女)")
    private Integer sex;

    /**
     * 电子邮件
     */
    @Schema(title = "电子邮箱", description = "电子邮箱")
    private String email;

    /**
     * 电话
     */
    @Schema(title = "电话", description = "电话")
    private String phone;

    /**
     * 冻结状态(0-正常，1-冻结）
     */
    @Schema(title = "冻结状态(0-正常，1-冻结）", description = "冻结状态(0-正常，1-冻结）")
    private Integer status;

}
