package com.qs.game.handler;

import com.qs.game.config.SysConfig;
import com.qs.game.model.even.Even;
import com.qs.game.model.even.OnCloseEven;
import com.qs.game.server.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.CloseReason;

/**
 * Created by zun.wei on 2018/11/21 14:07.
 * Description: 关闭事件处理类
 */
@Slf4j
@Component
public class OnCloseEvenHandler implements EvenHandler {


    @Override
    public void handler(Even even) {
        OnCloseEven onCloseEven = (OnCloseEven) even;
        WebSocketServer webSocketServer = onCloseEven.getWebSocketServer();
        CloseReason closeReason = onCloseEven.getCloseReason();
        String sid = onCloseEven.getSid();
        log.warn("OnCloseEvenHandler handler  sid:{},reason:{}", webSocketServer.getSid(), closeReason.getReasonPhrase());
        SysConfig.WEB_SOCKET_MAP.remove(sid); //从set中删除
    }

}
