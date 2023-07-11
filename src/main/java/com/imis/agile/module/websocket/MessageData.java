package com.imis.agile.module.websocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
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
@Schema(title = "WebSocket 消息 - 返回值", description = "WebSocket 消息 - 返回值")
public class MessageData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 发送方
     */
    @Schema(title = "发送方", hidden = true)
    private String sender;
    /**
     * 接收方
     */
    @Schema(title = "接收方", hidden = true)
    private String receiver;
    /**
     * 标题
     */
    @Schema(title = "标题")
    private String title;
    /**
     * 说明文字
     */
    @Schema(title = "说明文字")
    private String message;
    /**
     * 是否将 message 属性作为 HTML 片段处理
     */
    @Schema(title = "是否将 message 属性作为 HTML 片段处理")
    private Boolean html;
    /**
     * 主题样式，如果不在可选值内将被忽略
     */
    @Schema(title = "主题样式(success/warning/info/error)")
    private String type;
    /**
     * 显示时间, 毫秒。设为 0 则不会自动关闭
     */
    @Schema(title = "显示时间, 毫秒。设为 0 则不会自动关闭")
    private Integer duration;
    /**
     * 自定义弹出位置
     */
    @Schema(title = "自定义弹出位置(top-right/top-left/bottom-right/bottom-left)")
    private String position;

}
