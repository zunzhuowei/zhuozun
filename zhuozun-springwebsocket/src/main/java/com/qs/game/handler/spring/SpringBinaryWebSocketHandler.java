package com.qs.game.handler.spring;

import com.qs.game.constant.EvenType;
import com.qs.game.model.even.*;
import com.qs.game.socket.MessageRouter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import javax.annotation.Resource;

/**
 * Created by zun.wei on 2018/12/9.
 */
// (4) spring websocket binary config
@Slf4j
@Component
public class SpringBinaryWebSocketHandler extends BinaryWebSocketHandler {

    @Resource
    private SpringTextWebSocketHandler springTextWebSocketHandler;

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        MessageRouter.route(new OnBinaryEvent().setByteBuffer(message.getPayload())
                .setSpringWebSocketSession(new SpringWebSocketSession(session))
                .extractSid(), EvenType.ON_BYTE_MESSAGE);
    }

    @Override //当建立连接之后
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        MessageRouter.route(new OnOpenEvent()
                .setSpringWebSocketSession(new SpringWebSocketSession(session))
                .extractSid(), EvenType.ON_OPEN);
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        MessageRouter.route(new OnPongEvent().setByteBuffer(message.getPayload())
                .setSpringWebSocketSession(new SpringWebSocketSession(session))
                .extractSid(), EvenType.ON_PONE_MESSAGE);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        MessageRouter.route(new OnErrorEvent().setError(exception)
                .setSpringWebSocketSession(new SpringWebSocketSession(session))
                .extractSid(), EvenType.ON_ERROR_MESSAGE);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        MessageRouter.route(new OnCloseEvent().setReason(status.getReason())
                .setSpringWebSocketSession(new SpringWebSocketSession(session))
                .extractSid(), EvenType.ON_CLOSE);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        springTextWebSocketHandler.handleTextMessage(session, message);
    }
}
