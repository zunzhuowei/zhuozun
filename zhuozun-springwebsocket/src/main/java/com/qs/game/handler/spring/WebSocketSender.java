package com.qs.game.handler.spring;

import com.qs.game.job.SchedulingJob;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;

import static com.qs.game.config.SysConfig.WEB_SOCKET_MAP;

/**
 * Created by zun.wei on 2018/12/18 11:22.
 * Description: web socket发送消息
 */
public class WebSocketSender implements Serializable {


    /**
     * send binary message to client from given springWebSocketSession
     * @param springWebSocketSession com.qs.game.handler.spring.SpringWebSocketSession
     * @param bytesMsg byte array
     * @throws IOException java.io.IOException
     */
    public static void sendMessage(SpringWebSocketSession springWebSocketSession, byte[] bytesMsg) throws IOException {
        synchronized (springWebSocketSession.getWebSocketSession()) {
            if (springWebSocketSession.getWebSocketSession().isOpen()) {
                ByteBuffer byteBuffer = ByteBuffer.wrap(bytesMsg);
                BinaryMessage binaryMessage = new BinaryMessage(byteBuffer, true);
                springWebSocketSession.getWebSocketSession().sendMessage(binaryMessage);
            } else {
                WebSocketSender.closeWebSocket(springWebSocketSession);
            }
        }

    }

    /**
     * send string message to client from given springWebSocketSession
     * @param springWebSocketSession com.qs.game.handler.spring.SpringWebSocketSession
     * @param strMsg String message
     * @throws IOException java.io.IOException
     */
    public static void sendMessage(SpringWebSocketSession springWebSocketSession, String strMsg) throws IOException {
        synchronized (springWebSocketSession.getWebSocketSession()) {
            if (springWebSocketSession.getWebSocketSession().isOpen()) {
                springWebSocketSession.getWebSocketSession().sendMessage(new TextMessage(strMsg, true));
            } else {
                WebSocketSender.closeWebSocket(springWebSocketSession);
            }
        }
    }

    /**
     * send binary message to client from given springWebSocketSession
     * @param springWebSocketSession com.qs.game.handler.spring.SpringWebSocketSession
     * @param byteBuffer java.nio.ByteBuffer message
     * @throws IOException java.io.IOException
     */
    public static void sendMessage(SpringWebSocketSession springWebSocketSession, ByteBuffer byteBuffer) throws IOException {
        synchronized (springWebSocketSession.getWebSocketSession()) {
            if (springWebSocketSession.getWebSocketSession().isOpen()) {
                BinaryMessage binaryMessage = new BinaryMessage(byteBuffer, true);
                springWebSocketSession.getWebSocketSession().sendMessage(binaryMessage);
            } else {
                byteBuffer.clear();
                WebSocketSender.closeWebSocket(springWebSocketSession);
            }
        }
    }

    /**
     *  send WebSocketMessage message to client from given springWebSocketSession
     * @param springWebSocketSession com.qs.game.handler.spring.SpringWebSocketSession
     * @param message org.springframework.web.socket.WebSocketMessage
     * @throws IOException java.io.IOException
     */
    public static void sendMessage(SpringWebSocketSession springWebSocketSession, WebSocketMessage<?> message) throws IOException {
        synchronized (springWebSocketSession.getWebSocketSession()) {
            if (springWebSocketSession.getWebSocketSession().isOpen()) {
                springWebSocketSession.getWebSocketSession().sendMessage(message);
            } else {
                WebSocketSender.closeWebSocket(springWebSocketSession);
            }
        }
    }

    /**
     * close connect from given springWebSocketSession
     * @param springWebSocketSession com.qs.game.handler.spring.SpringWebSocketSession
     * @throws IOException java.io.IOException
     */
    public static void closeWebSocket(SpringWebSocketSession springWebSocketSession) throws IOException {
        synchronized (springWebSocketSession.getWebSocketSession()) {
            springWebSocketSession.getWebSocketSession().close();
            String sid = springWebSocketSession.getSid();
            WEB_SOCKET_MAP.remove(sid);
            //SchedulingJob.heartBeats.remove(sid);
        }
    }

}
