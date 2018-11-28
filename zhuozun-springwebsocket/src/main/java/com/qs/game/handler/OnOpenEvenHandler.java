package com.qs.game.handler;

import com.qs.game.config.SysConfig;
import com.qs.game.model.even.Even;
import com.qs.game.model.even.OnOpenEven;
import com.qs.game.socket.server.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by zun.wei on 2018/11/21 14:07.
 * Description: 打开连接处理类
 */
@Slf4j
@Component
public class OnOpenEvenHandler implements EvenHandler {


    @Override
    public void handler(Even even) {
        OnOpenEven onOpenEven = (OnOpenEven) even;
        WebSocketServer webSocketServer = onOpenEven.getWebSocketServer();
        String sid = onOpenEven.getSid();
        webSocketServer.setSession(onOpenEven.getSession());
        webSocketServer.setSid(sid);
        SysConfig.WEB_SOCKET_MAP.put(sid, webSocketServer);//加入 map 中
        log.info("OnOpenEvenHandler handler sid:{} -- online people:{}", sid, SysConfig.WEB_SOCKET_MAP.size());
        try {
            //onOpenEven.getSession().getBasicRemote().sendText("client sid:" +sid + ",连接成功!");
            //webSocketServer.sendObjectMessage(new UserTest().setId(1L).setUserName("张三").setPassWord("123").setSex((byte) 0));
            webSocketServer.sendBinaryMessage("{\"id\":1,\"passWord\":\"123\",\"sex\":0,\"userName\":\"张三\"}".getBytes());
        } catch (IOException e) {
            log.error("websocket IO异常");
        }

    }

}
