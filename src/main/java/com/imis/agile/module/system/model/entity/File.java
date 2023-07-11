package com.imis.agile.module.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.imis.agile.constant.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 文件存放
 * </p>
 *
 * @author XinLau
 * @since 2020-03-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_file")
public class File extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件名称
     */
    @TableField(value = "file_name")
    private String fileName;

    /**
     * 文件上传名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 文件类型
     */
    @TableField(value = "file_type")
    private String fileType;

    /**
     * 文件地址
     */
    @TableField(value = "file_url")
    private String fileUrl;

    /**
     * 文件大小
     */
    @TableField(value = "file_size")
    private Long fileSize;

    /**
     * 文件描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 删除状态（0-正常，1-已删除）
     * 强制要求逻辑删除的注解字段要放在最后
     *
     * @TableLogic(value = "0")
     */
    @TableField(value = "del_flag")
    private Integer delFlag;

}
