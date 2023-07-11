package com.imis.agile.module.system.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.imis.agile.constant.CommonConstant;
import com.imis.agile.util.IdCardUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 系统用户 - 分页查询返回值
 * </p>
 *
 * @author: XinLau
 * @create: 2020-01-10 16:11
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "系统用户 - 分页查询返回值", description = "系统用户 - 分页查询返回值 对象")
public class UserPageVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(title = "用户ID", description = "用户ID")
    private String id;

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
     * 头像
     */
    @Schema(title = "头像", description = "头像")
    private String avatar;

    /**
     * 身份证号码
     */
    @Schema(title = "身份证号码", description = "身份证号码")
    private String identityNumber;

    /**
     * 电子邮件
     */
    @Schema(title = "电子邮件", description = "电子邮件")
    private String email;

    /**
     * 电话
     */
    @Schema(title = "电话", description = "电话")
    private String phone;

    /**
     * 出生日期
     */
    @Schema(title = "出生日期", description = "出生日期")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = CommonConstant.NORM_DATE_PATTERN)
    @DateTimeFormat(pattern = CommonConstant.NORM_DATE_PATTERN)
    private LocalDate birthday;

    /**
     * 年龄
     */
    @Schema(title = "年龄", description = "年龄")
    @Setter(AccessLevel.NONE)
    private Integer age;

    /**
     * 性别(2-默认未知，1-男，0-女)
     */
    @Schema(title = "性别(2-默认未知，1-男，0-女)", description = "性别(2-默认未知，1-男，0-女)")
    @Setter(AccessLevel.NONE)
    private Integer sex;

    /**
     * 冻结状态(0-正常，1-冻结）
     */
    @Schema(title = "冻结状态(0-正常，1-冻结）", description = "冻结状态(0-正常，1-冻结）")
    private Integer status;

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
        this.age = IdCardUtil.getAge(identityNumber);
        this.sex = IdCardUtil.getSex(identityNumber);
    }

}
