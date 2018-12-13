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
        Map<String, Object> attrs = session.getAttributes();
        log.info("SpringBinaryWebSocketHandler handleBinaryMessage attrs = {}", attrs);
        String sid = attrs.get("sid") + "";
        MessageRouter.route(new OnBinaryEven().setByteBuffer(message.getPayload()).setSid(sid)
                .setSysWebSocket(new SpringWebSocketSession().setWebSocketSession(session).setSid(sid)), EvenType.ON_BYTE_MESSAGE);

        if (true) return;

        ByteBuffer byteBuffer = message.getPayload();

        int i = DataUtils.getByteByBuffer(byteBuffer);
        System.out.println("i = " + i);

        String tam = DataUtils.getStrByBuffer(byteBuffer, 15);
        System.out.println("tam = " + tam);

        int i2 = DataUtils.getIntByBuffer(byteBuffer);
        System.out.println("i2 = " + i2);

        int i3 = DataUtils.getShortByBuffer(byteBuffer);
        System.out.println("i3= " + i3);

        byte i4 = DataUtils.getByteByBuffer(byteBuffer);
        System.out.println("i4= " + (char)i4);

        byte i5 = DataUtils.getByteByBuffer(byteBuffer);
        System.out.println("i5= " + (char)i5);

    }

    @Override //当建立连接之后
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> attrs = session.getAttributes();
        log.info("SpringBinaryWebSocketHandler afterConnectionEstablished attrs = {}", attrs);
        String sid = attrs.get("sid") + "";
        MessageRouter.route(new OnOpenEven().setSid(sid)
                .setSysWebSocket(new SpringWebSocketSession().setWebSocketSession(session).setSid(sid)), EvenType.ON_OPEN);
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        Map<String, Object> attrs = session.getAttributes();
        log.info("SpringBinaryWebSocketHandler handlePongMessage attrs = {}", attrs);
        String sid = attrs.get("sid") + "";
        MessageRouter.route(new OnPongEven().setByteBuffer(message.getPayload()).setSid(sid)
                .setSysWebSocket(new SpringWebSocketSession().setWebSocketSession(session).setSid(sid)), EvenType.ON_PONE_MESSAGE);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        Map<String, Object> attrs = session.getAttributes();
        log.info("SpringBinaryWebSocketHandler handleTransportError attrs = {}", attrs);
        String sid = attrs.get("sid") + "";
        MessageRouter.route(new OnErrorEven().setError(exception).setSid(sid)
                .setSysWebSocket(new SpringWebSocketSession().setWebSocketSession(session).setSid(sid)), EvenType.ON_ERROR_MESSAGE);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Map<String, Object> attrs = session.getAttributes();
        log.info("SpringBinaryWebSocketHandler afterConnectionClosed attrs = {}", attrs);
        String sid = attrs.get("sid") + "";
        MessageRouter.route(new OnCloseEven().setReason(status.getReason())
                .setSid(sid).setSysWebSocket(new SpringWebSocketSession().setWebSocketSession(session).setSid(sid)), EvenType.ON_CLOSE);
    }

}
