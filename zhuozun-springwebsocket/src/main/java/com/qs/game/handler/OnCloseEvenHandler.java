package com.qs.game.handler;

import com.qs.game.config.SysConfig;
import com.qs.game.handler.spring.SpringWebSocketSession;
import com.qs.game.handler.spring.WebSocketSender;
import com.qs.game.model.even.Event;
import com.qs.game.model.even.OnCloseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by zun.wei on 2018/11/21 14:07.
 * Description: 关闭事件处理类
 */
@Slf4j
@Component
public class OnCloseEvenHandler implements EvenHandler {


    @Override
    public void handler(Event event) throws Exception {
        OnCloseEvent onCloseEven = (OnCloseEvent) event;
        SpringWebSocketSession springWebSocketSession = onCloseEven.getSpringWebSocketSession();
        String sid = onCloseEven.getSid();
        log.warn("OnCloseEvenHandler handler  sid:{},reason:{}", sid, onCloseEven.getReason());
        SysConfig.WEB_SOCKET_MAP.remove(sid); //从set中删除
        WebSocketSender.closeWebSocket(springWebSocketSession);

    }

}
