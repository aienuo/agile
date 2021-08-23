package com.imis.agile.module.api.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 * 文件存放 返回值
 *
 * @author XinLau
 * @since 2020-03-27
*/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "文件存放 返回值", description = "文件存放 返回值")
public class FileVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    @ApiModelProperty(value = "文件编号")
    private String id;
    /**
     * 文件名称
     */
    @ApiModelProperty(value = "文件名称")
    private String fileName;
    /**
     * 文件上传名
     */
    @ApiModelProperty(value = "文件上传名")
    private String realName;
    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型")
    private String fileType;
    /**
     * 文件地址
     */
    @ApiModelProperty(value = "文件地址")
    private String fileUrl;
    /**
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小")
    private Long fileSize;
    /**
     * 文件描述
     */
    @ApiModelProperty(value = "文件描述")
    private String description;

}
