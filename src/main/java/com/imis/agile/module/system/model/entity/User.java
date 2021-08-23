package com.imis.agile.module.system.model.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.imis.agile.constant.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author XinLau
 * @since 2020-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登录账号
     */
    @TableField(value = "username")
    private String username;

    /**
     * 登录密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * md5密码盐
     */
    @TableField(value = "salt")
    private String salt;

    /**
     * 头像
     */
    @TableField(value = "avatar", updateStrategy = FieldStrategy.IGNORED)
    private String avatar;

    /**
     * 真实姓名
     */
    @TableField(value = "realname")
    private String realname;

    /**
     * 身份证号
     */
    @TableField(value = "identity_number")
    private String identityNumber;

    /**
     * 电子邮箱
     */
    @TableField(value = "email", updateStrategy = FieldStrategy.IGNORED)
    private String email;

    /**
     * 手机号码
     */
    @TableField(value = "phone", updateStrategy = FieldStrategy.IGNORED)
    private String phone;

    /**
     * 出生日期
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(value = "birthday")
    private LocalDate birthday;

    /**
     * 性别(2-默认未知，1-男，0-女)
     */
    @TableField(value = "sex")
    private Integer sex;

    /**
     * 冻结状态(0-正常，1-冻结）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 删除状态（0-正常，1-已删除）
     * 强制要求逻辑删除的注解字段要放在最后
     *
     * @TableLogic(value = "0")
     */
    @TableField(value = "del_flag")
    private Integer delFlag;

}
