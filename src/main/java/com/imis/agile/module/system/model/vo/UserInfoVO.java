package com.imis.agile.module.system.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.imis.agile.constant.CommonConstant;
import com.imis.agile.util.IdCardUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 系统用户详细信息 - 查询返回值
 * </p>
 *
 * @author: XinLau
 * @create: 2020-01-10 16:11
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "系统用户详细信息 - 查询返回值", description = "系统用户详细信息 - 查询返回值")
public class UserInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "用户ID")
    private String id;

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
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码")
    private String identityNumber;

    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮件")
    private String email;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String phone;

    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = CommonConstant.NORM_DATE_PATTERN)
    @DateTimeFormat(pattern = CommonConstant.NORM_DATE_PATTERN)
    private LocalDate birthday;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    @Setter(AccessLevel.NONE)
    private Integer age;

    /**
     * 性别(2-默认未知，1-男，0-女)
     */
    @ApiModelProperty(value = "性别(2-默认未知，1-男，0-女)")
    @Setter(AccessLevel.NONE)
    private Integer sex;

    /**
     * 角色信息
     */
    @ApiModelProperty(value = "角色信息")
    private List<String> roleList;

    /**
     * 组织机构
     */
    @ApiModelProperty(value = "组织机构")
    private List<String> organizationList;

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
        this.age = IdCardUtil.getAge(identityNumber);
        this.sex = IdCardUtil.getSex(identityNumber);
    }

}
