package com.qs.game.test;

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
@ClientEndpoint
public class WebSocketTest {

    private static Log log = LogFactory.getLog(WebSocketTest.class);

    private String deviceId;

    private Session session;

    public WebSocketTest() {
    }

    private static final List<Session> list = new LinkedList<Session>();

    public WebSocketTest(String deviceId) {
        this.deviceId = deviceId;
    }

    protected boolean start() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        String uri = "ws://localhost:8600/websocket/" + deviceId;
        //String uri = "ws://192.168.1.27:8006/" + deviceId;
        System.out.println("Connecting to " + uri);
        try {
            session = container.connectToServer(WebSocketTest.class, URI.create(uri));
            System.out.println("count: " + deviceId);
            list.add(session);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        for (int i = 1; i < 500; i++) {
            WebSocketTest wSocketTest = new WebSocketTest(String.valueOf(i));
            if (!wSocketTest.start()) {
                System.out.println("测试结束。");
                break;
            }
        }

        for (; ; ) {
            list.forEach(e -> {
                try {
                    Thread.sleep(100);
                    boolean isOpen = e.isOpen();
                    if (isOpen) {

                        String msg = "abc";
                        int len = msg.length();

                        String tel = "123456789";

                        byte[] connent = ByteUtils.beginBuild()
                                .append('Q').append('S')
                                .append(len).append(msg)
                                .append(111)
                                .append('a')
                                .append(tel.length()).append(tel)
                                .buildByteArr();

                        e.getBasicRemote().sendBinary(ByteBuffer.wrap(connent));
                        e.getBasicRemote().flushBatch();
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
