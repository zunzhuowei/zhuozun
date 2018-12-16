package com.qs.game.socket.server;

import com.qs.game.job.SchedulingJob;
import com.qs.game.socket.MessageRouter;
import com.qs.game.socket.SysWebSocket;
import com.qs.game.socket.TextEncoder;
import com.qs.game.constant.EvenType;
import com.qs.game.model.even.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.concurrent.Future;

import static com.qs.game.config.SysConfig.WEBSOCKET_URI_PATH;
import static com.qs.game.config.SysConfig.WEB_SOCKET_MAP;

/**
 * Created by zun.wei on 2018/11/19 10:52.
 * Description: websocket 服务端(多例，该类中不支持spring 注入)
 *  经过压测，原生tomcat websocket 吞吐能力不及 spring 包装的websocket，
 *  故弃用该 server 节点
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Component
@Data
@Accessors(chain = true)
//@ServerEndpoint(value = WEBSOCKET_URI_PATH, encoders = {TextEncoder.class})
@Deprecated
public class WebSocketServer extends SysWebSocket {

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid
    private String sid = "";


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        MessageRouter.route(new OnOpenEven().setSession(session).setSid(sid)
                .setSysWebSocket(this), EvenType.ON_OPEN);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, CloseReason closeReason, @PathParam("sid") String sid) {
        MessageRouter.route(new OnCloseEven().setReason(closeReason.getReasonPhrase())
                .setSession(session).setSid(sid).setSysWebSocket(this), EvenType.ON_CLOSE);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("sid") String sid) {
        MessageRouter.route(new OnStrEven().setMessage(message).setSession(session).setSid(sid)
                .setSysWebSocket(this), EvenType.ON_STR_MESSAGE);

    }

    /**
     * 接收客户端 二进制格式请求数据
     */
    @OnMessage
    public void onMessage(ByteBuffer message, Session session, @PathParam("sid") String sid) {
        MessageRouter.route(new OnBinaryEven().setByteBuffer(message).setSession(session).setSid(sid)
                .setSysWebSocket(this), EvenType.ON_BYTE_MESSAGE);
    }

    /**
     * 客户端心跳
     */
    @OnMessage
    public void onMessage(PongMessage pongMessage, Session session, @PathParam("sid") String sid) {
        MessageRouter.route(new OnPongEven().setByteBuffer(pongMessage.getApplicationData())
                .setSession(session).setSid(sid).setSysWebSocket(this), EvenType.ON_PONE_MESSAGE);
    }

    /**
     * 当发生异常时
     */
    @OnError
    public void onError(Session session, Throwable error, @PathParam("sid") String sid) {
        MessageRouter.route(new OnErrorEven().setError(error).setSession(session).setSid(sid)
                .setSysWebSocket(this), EvenType.ON_ERROR_MESSAGE);
    }


    /**
     * Send the message, blocking until the message is sent.
     */
    public void sendTextMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * Send the message, blocking until the message is sent.
     */
    public void sendBinaryMessage(byte[] message) throws IOException {
        this.session.getBasicRemote().sendBinary(ByteBuffer.wrap(message));
    }

    /**
     * Send the message, blocking until the message is sent.
     * Encodes object as a message and sends it to the remote endpoint.
     */
    public void sendObjectMessage(Serializable object) throws IOException, EncodeException {
        this.session.getBasicRemote().sendObject(object);
    }


    /**
     * Send the message asynchronously
     */
    public Future<Void> sendTextMessageAsync(String message) {
        return this.session.getAsyncRemote().sendText(message);
    }

    /**
     * Send the message asynchronously
     */
    public Future<Void> sendTextMessageAsync(String message, long timeout) {
        RemoteEndpoint.Async async = this.session.getAsyncRemote();
        async.setSendTimeout(timeout);
        return async.sendText(message);
    }

    /**
     * @param completion Used to signal to the client when the message has been sent
     */
    public void sendTextMessageAsync(String message, SendHandler completion) {
        this.session.getAsyncRemote().sendText(message, completion);
    }

    /**
     * @param completion Used to signal to the client when the message has been sent
     * @param timeout    The new timeout for sending messages asynchronously
     *                   in milliseconds. A non-positive value means an
     *                   infinite timeout.
     */
    public void sendTextMessageAsync(String message, SendHandler completion, long timeout) {
        RemoteEndpoint.Async async = this.session.getAsyncRemote();
        async.setSendTimeout(timeout);
        async.sendText(message, completion);
    }


    public Future<Void> sendObjectMessageAsync(Serializable object) {
        return this.session.getAsyncRemote().sendObject(object);
    }

    public void sendObjectMessageAsync(Serializable object, SendHandler completion) {
        this.session.getAsyncRemote().sendObject(object, completion);
    }

    public Future<Void> sendObjectMessageAsync(Serializable object, long timeout) {
        RemoteEndpoint.Async async = this.session.getAsyncRemote();
        async.setSendTimeout(timeout);
        return async.sendObject(object);
    }

    public void sendObjectMessageAsync(Serializable object, SendHandler completion, long timeout) {
        RemoteEndpoint.Async async = this.session.getAsyncRemote();
        async.setSendTimeout(timeout);
        async.sendObject(object, completion);
    }


    /**
     * Send the message asynchronously
     */
    public Future<Void> sendBinaryMessageAsync(byte[] message) {
        return this.session.getAsyncRemote().sendBinary(ByteBuffer.wrap(message));
    }

    /**
     * Send the message asynchronously
     */
    public Future<Void> sendBinaryMessageAsync(byte[] message, long timeout) {
        RemoteEndpoint.Async async = this.session.getAsyncRemote();
        async.setSendTimeout(timeout);
        return async.sendBinary(ByteBuffer.wrap(message));
    }

    /**
     * @param completion Used to signal to the client when the message has been sent
     */
    public void sendBinaryMessageAsync(byte[] message, SendHandler completion) {
        this.session.getAsyncRemote().sendBinary(ByteBuffer.wrap(message), completion);
    }

    /**
     * @param completion Used to signal to the client when the message has been sent
     * @param timeout    The new timeout for sending messages asynchronously
     *                   in milliseconds. A non-positive value means an
     *                   infinite timeout.
     */
    public void sendBinaryMessageAsync(byte[] message, SendHandler completion, long timeout) {
        RemoteEndpoint.Async async = this.session.getAsyncRemote();
        async.setSendTimeout(timeout);
        async.sendBinary(ByteBuffer.wrap(message), completion);
    }

    @Override
    public void sendMessage(byte[] bytesMsg) throws IOException {
        synchronized (this) {
            if (Objects.nonNull(this.session)
                    && this.session.isOpen()) {
                this.session.getBasicRemote().sendBinary(ByteBuffer.wrap(bytesMsg), true);
            }
        }
    }

    @Override
    public void sendMessage(String strMsg) throws IOException {
        synchronized (this) {
            if (Objects.nonNull(this.session)
                    && this.session.isOpen()) {
                this.session.getBasicRemote().sendText(strMsg, true);
            }
        }
    }

    @Override
    public void sendMessage(ByteBuffer byteBuffer) throws IOException {
        synchronized (this) {
            if (Objects.nonNull(this.session)
                    && this.session.isOpen()) {
                this.session.getBasicRemote().sendBinary(byteBuffer, true);
            }
        }
    }

    @Override
    public void closeWebSocket(String sid) throws IOException {
        synchronized (this) {
            this.session.close();
            WEB_SOCKET_MAP.remove(sid);
            SchedulingJob.heartBeats.remove(sid);
        }
    }

}
