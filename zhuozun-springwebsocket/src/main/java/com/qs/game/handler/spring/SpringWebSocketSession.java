package com.qs.game.handler.spring;

import com.qs.game.socket.SysWebSocket;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.nio.ByteBuffer;

import static com.qs.game.config.SysConfig.WEB_SOCKET_MAP;

/**
 * Created by zun.wei on 2018/12/9.
 *
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
@Accessors(chain = true)
public class SpringWebSocketSession extends SysWebSocket {

    private WebSocketSession webSocketSession;

    //接收sid
    private String sid;

    @Override
    public void sendMessage(byte[] bytesMsg) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytesMsg);
        BinaryMessage binaryMessage = new BinaryMessage(byteBuffer);
        webSocketSession.sendMessage(binaryMessage);
    }

    @Override
    public void sendMessage(String strMsg) throws IOException {
        webSocketSession.sendMessage(new TextMessage(strMsg));
    }

    @Override
    public void sendMessage(ByteBuffer byteBuffer) throws IOException {
        BinaryMessage binaryMessage = new BinaryMessage(byteBuffer);
        webSocketSession.sendMessage(binaryMessage);
    }

    @Override
    public void closeWebSocket() throws IOException {
        webSocketSession.close();
        WEB_SOCKET_MAP.remove(this.sid);
    }

}
