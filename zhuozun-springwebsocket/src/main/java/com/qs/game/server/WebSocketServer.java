package com.qs.game.server;

import com.qs.game.config.MessageRouter;
import com.qs.game.config.SysConfig;
import com.qs.game.constant.EvenType;
import com.qs.game.model.even.*;
import com.qs.game.utils.ByteUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by zun.wei on 2018/11/19 10:52.
 * Description:
 */
@ServerEndpoint(value = "/websocket/{sid}",configurator = SpringConfigurator.class)
//@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {

    private static Log log = LogFactory.getLog(WebSocketServer.class);

    @Autowired
    private MessageRouter messageRouter;

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid
    private String sid = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        messageRouter.router(new Even().setSession(session).setSid(sid), EvenType.ON_OPEN);
        this.session = session;
        this.sid = sid;
        SysConfig.webSocketSet.add(this); //加入set中
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        messageRouter.router(new Even().setSession(session), EvenType.ON_CLOSE);
        SysConfig.webSocketSet.remove(this); //从set中删除
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        messageRouter.router(new OnStrEven().setMessage(message).setSession(session).setSid(sid), EvenType.ON_STR_MESSAGE);
        log.info("收到来自窗口" + sid + "的信息:" + message);
        //群发消息
        for (WebSocketServer item : SysConfig.webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  接收客户端 二进制格式请求数据
     */
    @OnMessage
    public void onMessage(ByteBuffer message, Session session) {
        messageRouter.router(new OnBinaryEven().setByteBuffer(message).setSession(session).setSid(sid), EvenType.ON_BYTE_MESSAGE);
        SysConfig.executor.execute(() -> {
            ByteBuffer duplicate = message.duplicate();
            char q = message.getChar();
            char s = message.getChar();
            System.out.println("q = " + q);
            System.out.println("s = " + s);

            int msgLen = message.getInt();
            String msg = ByteUtils.getStr(message, msgLen);
            System.out.println("msg = " + msg);

            int tail = message.getInt();
            System.out.println("tail = " + tail);

            char c = message.getChar();

            int telLen = message.getInt();
            String tel = ByteUtils.getStr(message, telLen);

            System.out.println("tel = " + tel);
            System.out.println("c = " + c);

            log.info("收到来自窗口" + sid + "的信息:" + message);
            //群发消息
            for (WebSocketServer item : SysConfig.webSocketSet) {
                try {
                    item.sendMessage(message.toString());
                    item.session.getBasicRemote().sendBinary(duplicate);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     *  客户端心跳
     */
    @OnMessage
    public void onMessage(PongMessage pongMessage, Session session) {
        messageRouter.router(new OnPongEven().setPongMessage(pongMessage).setSession(session).setSid(sid), EvenType.ON_PONE_MESSAGE);
        System.out.println("pongMessage = " + pongMessage);
    }

    /**
     * 当发生异常时
     */
    @OnError
    public void onError(Session session, Throwable error) {
        messageRouter.router(new OnErrorEven().setError(error).setSession(session), EvenType.ON_ERROR_MESSAGE);
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     */
    /*public static void sendInfo(String message, @PathParam("sid") String sid) throws IOException {
        log.info("推送消息到窗口" + sid + "，推送内容:" + message);
        for (WebSocketServer item : SysConfig.webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if (sid == null) {
                    item.sendMessage(message);
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }*/

}
