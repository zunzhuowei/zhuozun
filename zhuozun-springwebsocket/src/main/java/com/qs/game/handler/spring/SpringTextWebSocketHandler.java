package com.qs.game.handler.spring;

import com.qs.game.constant.EvenType;
import com.qs.game.model.even.*;
import com.qs.game.socket.MessageRouter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;

/**
 * Created by zun.wei on 2018/12/9.
 */
// (3) spring websocket text config
@Slf4j
@Component
public class SpringTextWebSocketHandler extends TextWebSocketHandler {

    @Resource
    private SpringBinaryWebSocketHandler springBinaryWebSocketHandler;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        MessageRouter.route(new OnStrEvent().setMessage(message.getPayload())
                .setSpringWebSocketSession(new SpringWebSocketSession(session))
                .extractSid(), EvenType.ON_STR_MESSAGE);
    }

    @Override
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
                .setSpringWebSocketSession(new SpringWebSocketSession(session)), EvenType.ON_ERROR_MESSAGE);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        MessageRouter.route(new OnCloseEvent().setReason(status.getReason()).setCode(status.getCode())
                .setSpringWebSocketSession(new SpringWebSocketSession(session))
                .extractSid(), EvenType.ON_CLOSE);
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        springBinaryWebSocketHandler.handleBinaryMessage(session, message);
    }

}
