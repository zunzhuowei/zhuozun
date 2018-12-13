package com.qs.game.handler.spring;

import com.qs.game.constant.EvenType;
import com.qs.game.model.even.*;
import com.qs.game.socket.MessageRouter;
import com.qs.game.utils.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.nio.ByteBuffer;
import java.util.Map;

import static com.qs.game.config.SysConfig.WEB_SOCKET_MAP;

/**
 * Created by zun.wei on 2018/12/9.
 */
// (4) spring websocket binary config
@Slf4j
@Component
public class SpringBinaryWebSocketHandler extends BinaryWebSocketHandler {


    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        MessageRouter.route(new OnBinaryEven().setByteBuffer(message.getPayload())
                .setSysWebSocket(new SpringWebSocketSession().setWebSocketSession(session)), EvenType.ON_BYTE_MESSAGE);
    }

    @Override //当建立连接之后
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        MessageRouter.route(new OnOpenEven()
                .setSysWebSocket(new SpringWebSocketSession().setWebSocketSession(session)), EvenType.ON_OPEN);
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        MessageRouter.route(new OnPongEven().setByteBuffer(message.getPayload())
                .setSysWebSocket(new SpringWebSocketSession().setWebSocketSession(session)), EvenType.ON_PONE_MESSAGE);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        MessageRouter.route(new OnErrorEven().setError(exception)
                .setSysWebSocket(new SpringWebSocketSession().setWebSocketSession(session)), EvenType.ON_ERROR_MESSAGE);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        MessageRouter.route(new OnCloseEven().setReason(status.getReason())
                .setSysWebSocket(new SpringWebSocketSession().setWebSocketSession(session)), EvenType.ON_CLOSE);
    }

}
