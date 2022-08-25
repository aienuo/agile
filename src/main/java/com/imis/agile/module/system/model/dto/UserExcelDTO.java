package com.imis.agile.module.system.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.imis.agile.annotation.Excel;
import com.imis.agile.util.IdCardUtil;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 系统用户 - Excel对象
 * </p>
 *
 * @author: XinLau
 * @create: 2020-01-10 16:11
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "系统用户 - Excel对象", description = "系统用户 - Excel对象")
public class UserExcelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登录账号
     */
    @Excel(name = "登录账号", prompt = "登陆帐号应该是唯一的")
    private String username;

    /**
     * 真实姓名
     */
    @Excel(name = "真实姓名")
    private String realname;

    /**
     * 头像
     */
    @Excel(name = "头像", prompt = "有需要的可上传图片")
    private String avatar;

    /**
     * 身份证号码
     */
    @Excel(name = "身份证号码", prompt = "身份证号码必须是真实的")
    private String identityNumber;

    /**
     * 电子邮件
     */
    @Excel(name = "电子邮件", prompt = "电子邮件必须是真实的")
    private String email;

    /**
     * 电话
     */
    @Excel(name = "电话", prompt = "电话必须是真实的")
    private String phone;

    /**
     * 出生日期
     */
    @Excel(name = "出生日期", type = Excel.Type.EXPORT)
    private LocalDate birthday;

    public LocalDate getBirthday() {
        this.birthday = IdCardUtil.getBirthByIdCard(this.identityNumber);
        return this.birthday;
    }

    /**
     * 年龄
     */
    @Excel(name = "年龄", type = Excel.Type.EXPORT)
    private Integer age;

    public Integer getAge() {
        this.age = IdCardUtil.getAge(this.identityNumber);
        return this.age;
    }

    /**
     * 性别(2-默认未知，1-男，0-女)
     */
    @Excel(name = "性别",dictCode = "sex", type = Excel.Type.EXPORT)
    private Integer sex;

    public Integer getSex() {
        this.sex = IdCardUtil.getSex(this.identityNumber);
        return this.sex;
    }
}
