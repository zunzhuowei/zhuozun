package com.qs.game.server;

import com.qs.game.config.MessageRouter;
import com.qs.game.constant.EvenType;
import com.qs.game.model.even.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by zun.wei on 2018/11/19 10:52.
 * Description: websocket 服务端(多例，该类中不支持spring 注入)
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{sid}")
public class WebSocketServer {

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid
    private String sid = "";


    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        MessageRouter.route(new OnOpenEven().setSession(session).setSid(sid)
                .setWebSocketServer(this), EvenType.ON_OPEN);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, CloseReason closeReason, @PathParam("sid") String sid) {
        MessageRouter.route(new OnCloseEven().setCloseReason(closeReason).setSession(session)
                .setSid(sid).setWebSocketServer(this), EvenType.ON_CLOSE);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("sid") String sid) {
        MessageRouter.route(new OnStrEven().setMessage(message).setSession(session).setSid(sid)
                .setWebSocketServer(this), EvenType.ON_STR_MESSAGE);

    }

    /**
     *  接收客户端 二进制格式请求数据
     */
    @OnMessage
    public void onMessage(ByteBuffer message, Session session, @PathParam("sid") String sid) {
        MessageRouter.route(new OnBinaryEven().setByteBuffer(message).setSession(session).setSid(sid)
                .setWebSocketServer(this), EvenType.ON_BYTE_MESSAGE);
    }

    /**
     *  客户端心跳
     */
    @OnMessage
    public void onMessage(PongMessage pongMessage, Session session, @PathParam("sid") String sid) {
        MessageRouter.route(new OnPongEven().setPongMessage(pongMessage).setSession(session).setSid(sid)
                .setWebSocketServer(this), EvenType.ON_PONE_MESSAGE);
    }

    /**
     * 当发生异常时
     */
    @OnError
    public void onError(Session session, Throwable error, @PathParam("sid") String sid) {
        MessageRouter.route(new OnErrorEven().setError(error).setSession(session).setSid(sid)
                .setWebSocketServer(this), EvenType.ON_ERROR_MESSAGE);
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

}
