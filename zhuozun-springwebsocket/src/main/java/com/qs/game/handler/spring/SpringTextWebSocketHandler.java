package com.qs.game.handler.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

import static com.qs.game.config.SysConfig.WEB_SOCKET_MAP;

/**
 * Created by zun.wei on 2018/12/9.
 */
// (3) spring websocket text config
@Slf4j
@Component
public class SpringTextWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        String MyHandler = message.getPayload();
        System.out.println("MyHandler = " + MyHandler);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> attrs = session.getAttributes();
        log.info("SpringTextWebSocketHandler afterConnectionEstablished attrs = {}", attrs);
        String sid = attrs.get("sid") + "";
        SpringWebSocketSession springWebSocketSession = new SpringWebSocketSession().setWebSocketSession(session).setSid(sid);
        WEB_SOCKET_MAP.put(sid, springWebSocketSession);
        super.afterConnectionEstablished(session);
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        super.handlePongMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Map<String, Object> attrs = session.getAttributes();
        log.info("SpringTextWebSocketHandler afterConnectionClosed attrs = {}", attrs);
        String sid = attrs.get("sid") + "";
        WEB_SOCKET_MAP.remove(sid);
        super.afterConnectionClosed(session, status);
    }

}
