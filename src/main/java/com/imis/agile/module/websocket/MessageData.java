package com.imis.agile.module.websocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * WebSocket 消息 - 返回值
 * </p>
 *
 * @author XinLau
 * @since 2020-03-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "WebSocket 消息 - 返回值", description = "WebSocket 消息 - 返回值")
public class MessageData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发送方
     */
    @ApiModelProperty(value = "发送方", hidden = true)
    private String sender;
    /**
     * 接收方
     */
    @ApiModelProperty(value = "接收方", hidden = true)
    private String receiver;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;
    /**
     * 说明文字
     */
    @ApiModelProperty(value = "说明文字")
    private String message;
    /**
     * 是否将 message 属性作为 HTML 片段处理
     */
    @ApiModelProperty(value = "是否将 message 属性作为 HTML 片段处理")
    private Boolean html;
    /**
     * 主题样式，如果不在可选值内将被忽略
     */
    @ApiModelProperty(value = "主题样式(success/warning/info/error)")
    private String type;
    /**
     * 显示时间, 毫秒。设为 0 则不会自动关闭
     */
    @ApiModelProperty(value = "显示时间, 毫秒。设为 0 则不会自动关闭")
    private Integer duration;
    /**
     * 自定义弹出位置
     */
    @ApiModelProperty(value = "自定义弹出位置(top-right/top-left/bottom-right/bottom-left)")
    private String position;

    @Override
    public String toString() {
        return "{ " +
                "\"title\" : \"" + this.getTitle()
                + "\", \"message\" : \"" + this.getMessage()
                + "\", \"html\" : " + this.getHtml()
                + ", \"type\" : \"" + this.getType()
                + "\", \"duration\" : " + this.getDuration()
                + ", \"position\" : \"" + this.getPosition()
                + "\" }";
    }

}
