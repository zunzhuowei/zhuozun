package com.qs.game.handler;

import com.qs.game.config.SysConfig;
import com.qs.game.handler.spring.SpringWebSocketSession;
import com.qs.game.model.even.Event;
import com.qs.game.model.even.OnOpenEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by zun.wei on 2018/11/21 14:07.
 * Description: 打开连接处理类
 */
@Slf4j
@Component
public class OnOpenEvenHandler implements EvenHandler {


    @Override
    public void handler(Event event) throws Exception {
        OnOpenEvent onOpenEven = (OnOpenEvent) event;
        SpringWebSocketSession springWebSocketSession = onOpenEven.getSpringWebSocketSession();
        String sid = onOpenEven.getSid();
        SysConfig.WEB_SOCKET_MAP.put(sid, springWebSocketSession);//加入 map 中
        log.info("OnOpenEvenHandler handler sid:{} -- online people:{}", sid, SysConfig.WEB_SOCKET_MAP.size());

    }

}
