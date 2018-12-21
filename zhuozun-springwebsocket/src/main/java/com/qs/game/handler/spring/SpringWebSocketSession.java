package com.qs.game.handler.spring;

import com.qs.game.job.SchedulingJob;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Objects;

import static com.qs.game.config.SysConfig.SID;
import static com.qs.game.config.SysConfig.WEB_SOCKET_MAP;

/**
 * Created by zun.wei on 2018/12/9.
 */
@Slf4j
@Data
@Accessors(chain = true)
public class SpringWebSocketSession implements Serializable {

    private final WebSocketSession webSocketSession;

    //接收sid
    private String sid;

    public SpringWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
        if (Objects.nonNull(webSocketSession)) {
            Map<String, Object> context = webSocketSession.getAttributes();
            this.setSid(context.get(SID) + "");
        }
    }

/*

    public void sendMessage(byte[] bytesMsg) throws IOException {
        synchronized (this.webSocketSession) {
            if (this.webSocketSession.isOpen()) {
                ByteBuffer byteBuffer = ByteBuffer.wrap(bytesMsg);
                BinaryMessage binaryMessage = new BinaryMessage(byteBuffer, true);
                this.webSocketSession.sendMessage(binaryMessage);
            } else {
                this.closeWebSocket(this.sid);
            }
        }

    }

    public void sendMessage(String strMsg) throws IOException {
        synchronized (this.webSocketSession) {
            if (this.webSocketSession.isOpen()) {
                this.webSocketSession.sendMessage(new TextMessage(strMsg, true));
            } else {
                this.closeWebSocket(this.sid);
            }
        }
    }

    public void sendMessage(ByteBuffer byteBuffer) throws IOException {
        synchronized (this.webSocketSession) {
            if (this.webSocketSession.isOpen()) {
                BinaryMessage binaryMessage = new BinaryMessage(byteBuffer, true);
                this.webSocketSession.sendMessage(binaryMessage);
            } else {
                byteBuffer.clear();
                this.closeWebSocket(this.sid);
            }
        }
    }

    public void sendMessage(WebSocketMessage<?> message) throws IOException {
        synchronized (this.webSocketSession) {
            if (this.webSocketSession.isOpen()) {
                this.webSocketSession.sendMessage(message);
            } else {
                this.closeWebSocket(this.sid);
            }
        }
    }

    public void closeWebSocket(String sid) throws IOException {
        synchronized (this.webSocketSession) {
            this.webSocketSession.close();
            WEB_SOCKET_MAP.remove(sid);
            SchedulingJob.heartBeats.remove(sid);
        }
    }
*/

}
