package com.imis.agile.module.websocket;

import com.imis.agile.util.AgileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <p>
 * WebSocketServer<br>
 * 监听连接的建立关闭、消息接收 处理类
 * </p>
 *
 * @author XinLau
 * @since 2020-03-12
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{username}")
public class WebSocketServer {

    /**
     * Session
     */
    private Session session;

    /**
     * WebSocketServer
     */
    private static final CopyOnWriteArraySet<WebSocketServer> WEB_SOCKET_SET = new CopyOnWriteArraySet<>();

    /**
     * Session 会话池
     */
    public static Map<String, Session> sessionPool = new HashMap<>();

    /**
     * 出现错误
     *
     * @param session  - Session
     * @param username - 用户唯一标识
     * @param error    - Throwable
     */
    @OnError
    public void onError(Session session, @PathParam(value = "username") String username, Throwable error) {
        log.error("【WebSocket】发生错误：用户标识 {} Session ID：{} 出现错误 {}", username, session.getId(), error.getMessage());
    }

    /**
     * 开启 Session 会话
     *
     * @param session  - Session
     * @param username - 用户唯一标识
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "username") String username) {
        try {
            this.session = session;
            WEB_SOCKET_SET.add(this);
            sessionPool.put(username, session);
            log.info("【WebSocket】有新的连接，当前会话总数为:" + WEB_SOCKET_SET.size());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 关闭 Session 会话
     *
     * @param username - 用户唯一标识
     */
    @OnClose
    public void onClose(@PathParam(value = "username") String username) {
        try {
            WEB_SOCKET_SET.remove(this);
            sessionPool.remove(username);
            log.info("【WebSocket】有连接断开，当前会话总数为:" + WEB_SOCKET_SET.size());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param username    - 用户唯一标识
     * @param messageData - 消息
     */
    @OnMessage
    public void onMessage(@PathParam(value = "username") String username, String messageData) {
        log.debug("【WebSocket】收到客户端 {} 的消息: {}", username, messageData);
        MessageData message = AgileUtil.stringToClass(messageData, MessageData.class);
        this.sendMessageToUser(message);
    }

    /**
     * 发送消息给指定用户
     *
     * @param messageData - 消息
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/6 14:26
     */
    public void sendMessageToUser(final MessageData messageData) {
        if (AgileUtil.isNotEmpty(messageData)) {
            Session session = sessionPool.get(messageData.getReceiver());
            if (AgileUtil.isNotEmpty(session) && session.isOpen()) {
                try {
                    log.info("【WebSocket】 进行消息发送: {}", messageData);
                    session.getAsyncRemote().sendText(AgileUtil.classToString(messageData));
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
            // todo:调用消息保存，并标注是否发送成功
        }
    }

    /**
     * 群发消息给指定用户
     *
     * @param messageDataList - 消息
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/6 14:26
     */
    public void sendMessageToUserList(final List<MessageData> messageDataList) {
        messageDataList.forEach(
                messageData -> this.sendMessageToUser(messageData)
        );
    }

    /**
     * 群发消息给所有在线用户
     *
     * @param messageData - 消息
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/6 14:26
     */
    public void sendMessageToOnlineUser(final String messageData) {
        log.info("【WebSocket】 发送群发消息: {}", messageData);
        WEB_SOCKET_SET.forEach(
                webSocketServer -> {
                    try {
                        if (webSocketServer.session.isOpen()) {
                            webSocketServer.session.getAsyncRemote().sendText(messageData);
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
        );
    }

}