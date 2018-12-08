package com.qs.game.handler.spring;

import com.qs.game.utils.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.nio.ByteBuffer;

/**
 * Created by zun.wei on 2018/12/9.
 */
// (4) spring websocket binary config
@Slf4j
@Component
public class SpringBinaryWebSocketHandler extends BinaryWebSocketHandler {


    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
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

        if (true) return;
    }

    @Override //当建立连接之后
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.wrap("当建立连接之后".getBytes());
        BinaryMessage binaryMessage = new BinaryMessage(byteBuffer);
        session.sendMessage(binaryMessage);
        session.sendMessage(new TextMessage("当建立连接之后v"));
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
        super.afterConnectionClosed(session, status);
    }

}
