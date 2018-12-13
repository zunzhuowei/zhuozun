package com.qs.game.socket.client;

import com.qs.game.socket.TextEncoder;
import com.qs.game.model.communication.UserTest;
import com.qs.game.utils.ByteUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zun.wei on 2018/11/19 16:26.
 * Description:
 */
@ClientEndpoint(encoders = {TextEncoder.class})
public class WebSocketClient {

    private static Log log = LogFactory.getLog(WebSocketClient.class);

    private String deviceId;

    private Session session;

    public WebSocketClient() {
    }

    private static final List<Session> list = new LinkedList<Session>();

    public WebSocketClient(String deviceId) {
        this.deviceId = deviceId;
    }

    protected boolean start(int choose) {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        String uri = "ws://localhost:8600/websocket/" + deviceId;
        if (choose == 0) uri = "ws://localhost:8600/websocket2/" + deviceId;
        //String uri = "ws://192.168.1.27:8006/" + deviceId;
        System.out.println("Connecting to " + uri);
        try {
            session = container.connectToServer(WebSocketClient.class, URI.create(uri));
            System.out.println("count: " + deviceId);
            list.add(session);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        for (int i = 1; i < 20000; i++) {
            WebSocketClient wSocketTest = new WebSocketClient(String.valueOf(i));
            int ii = i % 2;
            if (!wSocketTest.start(ii)) {
                System.out.println("测试结束。");
                break;
            }
        }

        for (; ; ) {
            list.forEach(e -> {
                try {
                    Thread.sleep(1);
                    boolean isOpen = e.isOpen();
                    if (isOpen) {

                        String msg = "abc";
                        int len = msg.length();

                        String tel = "123456789";

                        byte[] connent = ByteUtils.beginBuild()
                                .append('Q').append('S')
                                .append(1000)
                                .append(len).append(msg)
                                .append(111)
                                .append('a')
                                .append(tel.length()).append(tel)
                                .buildByteArr();

                        e.getBasicRemote().sendBinary(ByteBuffer.wrap(connent));
                    }
                } catch (IOException | InterruptedException e1) {
                    e1.printStackTrace();
                }
            });
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口服务端" + "的信息:" + message);
    }


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        log.debug("连接建立成功调用的方法 ---::");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        log.debug("连接关闭调用的方法v ---::");
    }

    /**
     *  接收服务端 二进制格式请求数据
     */
    @OnMessage
    public void onMessage(ByteBuffer message, Session session) {
        byte[] msgs = message.array();
        log.debug("WebSocketClient onMessage msgs -------::" + new String(msgs));
        message.clear();
        log.debug("接收服务端 二进制格式请求数据 -----------");
    }

    /**
     *  服务器心跳
     * @param pongMessage
     * @param session
     */
    @OnMessage
    public void onMessage(PongMessage pongMessage, Session session) {
        System.out.println("pongMessage = " + pongMessage);
    }

    /**
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

}
