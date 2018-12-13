package com.qs.game.socket;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created by zun.wei on 2018/12/9.
 */
@Data
@Accessors(chain = true)
@Slf4j
public class SysWebSocket implements Serializable {

    @Deprecated
    private Session session;

    //接收sid
    @Deprecated
    private String sid = "";

    private WebSocketSession webSocketSession;

    public void sendMessage(byte[] bytesMsg) throws IOException {}

    public void sendMessage(String strMsg) throws IOException {}

    public void sendMessage(ByteBuffer byteBuffer) throws IOException {}

    public void closeWebSocket(String sid) throws IOException {}

}
