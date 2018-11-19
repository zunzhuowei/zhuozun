package com.qs.game.test;

import javax.websocket.ClientEndpoint;
import java.net.URI;


import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        for (int i = 1; i < 50000; i++) {
            WebSocketTest wSocketTest = new WebSocketTest(String.valueOf(i));
            if (!wSocketTest.start()) {
                System.out.println("测试结束。");
                break;
            }
        }
    }

}
