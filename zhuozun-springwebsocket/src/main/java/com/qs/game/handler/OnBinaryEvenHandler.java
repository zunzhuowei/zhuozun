package com.qs.game.handler;

import com.qs.game.config.SysConfig;
import com.qs.game.model.communication.UserTest;
import com.qs.game.model.even.Even;
import com.qs.game.model.even.OnBinaryEven;
import com.qs.game.socket.server.WebSocketServer;
import com.qs.game.utils.ByteUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by zun.wei on 2018/11/21 14:07.
 * Description: 二进制消息事件处理类
 */
@Slf4j
@Component
public class OnBinaryEvenHandler implements EvenHandler {


    @Override
    public void handler(Even even) {
        OnBinaryEven onBinaryEven = (OnBinaryEven) even;
        ByteBuffer message = onBinaryEven.getByteBuffer();
        String sid = onBinaryEven.getSid();
        WebSocketServer webSocketServer = onBinaryEven.getWebSocketServer();
        try {
            webSocketServer.sendObjectMessage(new UserTest().setId(1L).setUserName("张三").setPassWord("123").setSex((byte) 0));
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }

        int i = message.get();
        System.out.println("i = " + i);

        String tam = ByteUtils.getStr(message, 15);
        System.out.println("tam = " + tam);

        int i2 = message.getInt();
        System.out.println("i2 = " + i2);

        if (true) return;

        SysConfig.THREAD_POOL_EXECUTOR.execute(() -> {
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

            try {
                webSocketServer.getSession().getBasicRemote().sendBinary(duplicate);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //群发消息
            /*SysConfig.WEB_SOCKET_MAP.forEachValue(100L, webSocketServer -> {
                try {
                    webSocketServer.sendMessage(message.toString());
                    webSocketServer.getSession().getBasicRemote().sendBinary(duplicate);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });*/

        });
    }

}
