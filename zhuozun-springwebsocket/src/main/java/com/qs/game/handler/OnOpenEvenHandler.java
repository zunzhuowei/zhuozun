package com.qs.game.handler;

import com.qs.game.config.SysConfig;
import com.qs.game.handler.spring.SpringWebSocketSession;
import com.qs.game.model.even.Even;
import com.qs.game.model.even.OnOpenEven;
import com.qs.game.socket.SysWebSocket;
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
    public void handler(Even even) throws Exception {
        OnOpenEven onOpenEven = (OnOpenEven) even;
        SysWebSocket sysWebSocket = onOpenEven.getSysWebSocket();
        String sid = onOpenEven.getSid();
        sysWebSocket.setSession(onOpenEven.getSession());
        sysWebSocket.setSid(sid);
        sysWebSocket.setWebSocketSession(sysWebSocket.getWebSocketSession());
        SysConfig.WEB_SOCKET_MAP.put(sid, sysWebSocket);//加入 map 中
        log.info("OnOpenEvenHandler handler sid:{} -- online people:{}", sid, SysConfig.WEB_SOCKET_MAP.size());

    }

}
