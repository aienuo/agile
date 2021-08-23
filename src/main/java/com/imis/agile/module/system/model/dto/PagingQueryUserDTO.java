package com.imis.agile.module.system.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.imis.agile.constant.base.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

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
@ApiModel(value="系统用户 - 分页查询参数", description="系统用户 - 分页查询参数")
@EqualsAndHashCode(callSuper = false)
public class PagingQueryUserDTO extends BasePageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号")
    private Long id;

    /**
     * 登录账号
     */
    @ApiModelProperty(value = "登录账号")
    private String username;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realname;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String identityNumber;

    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期-始")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    @ApiModelProperty(value = "出生日期-终")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdayEnd;

    /**
     * 性别(2-默认未知，1-男，0-女)
     */
    @ApiModelProperty(value = "性别(2-默认未知，1-男，0-女)")
    private Integer sex;

    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮箱")
    private String email;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String phone;

    /**
     * 冻结状态(0-正常，1-冻结）
     */
    @ApiModelProperty(value = "冻结状态(0-正常，1-冻结）")
    private Integer status;

}
