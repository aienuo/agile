package com.aienuo.agile.module.api.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
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
@Schema(title = "文件存放 返回值", description = "文件存放 返回值")
public class FileVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    @Schema(title = "文件编号", description = "文件编号")
    private String id;
    /**
     * 文件名称
     */
    @Schema(title = "文件名称", description = "文件名称")
    private String fileName;
    /**
     * 文件上传名
     */
    @Schema(title = "文件上传名", description = "文件上传名")
    private String realName;
    /**
     * 文件类型
     */
    @Schema(title = "文件类型", description = "文件类型")
    private String fileType;
    /**
     * 文件地址
     */
    @Schema(title = "文件地址", description = "文件地址")
    private String fileUrl;
    /**
     * 文件大小
     */
    @Schema(title = "文件大小", description = "文件大小")
    private Long fileSize;
    /**
     * 文件描述
     */
    @Schema(title = "文件描述", description = "文件描述")
    private String description;

}
