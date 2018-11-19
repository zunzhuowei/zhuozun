package com.qs.game.test;

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
        for (int i = 1; i < 2; i++) {
            WebSocketTest wSocketTest = new WebSocketTest(String.valueOf(i));
            if (!wSocketTest.start()) {
                System.out.println("测试结束。");
                break;
            }
        }

        for (;;) {
            list.forEach(e -> {
                try {
                    Thread.sleep(1000);
                    boolean isOpen = e.isOpen();
                    if (isOpen) {
                        e.getBasicRemote().sendBinary(ByteBuffer.wrap(charToByte('Q')), false);
                        e.getBasicRemote().sendBinary(ByteBuffer.wrap(charToByte('S')), false);
                        String msg = "abc";
                        int len = msg.length();
                        e.getBasicRemote().sendBinary(ByteBuffer.wrap(intToByteArray(len)), false);
                        e.getBasicRemote().sendBinary(ByteBuffer.wrap(msg.getBytes()), false);
                        e.getBasicRemote().sendBinary(ByteBuffer.wrap(intToByteArray(111)), true);
                        e.getBasicRemote().flushBatch();
                    }
                } catch (IOException | InterruptedException e1) {
                    e1.printStackTrace();
                }
            });
        }
    }

    public static int byteArrayToInt(byte[] b) {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static byte[] intToByteArray(int a) {
        return new byte[] {
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    public static byte[] charToByte(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }

    public static char byteToChar(byte[] b) {
        return (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
    }

}
