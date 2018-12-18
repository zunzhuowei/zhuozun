package com.qs.game.handler;

import com.qs.game.config.SysConfig;
import com.qs.game.handler.spring.SpringWebSocketSession;
import com.qs.game.handler.spring.WebSocketSender;
import com.qs.game.model.even.Event;
import com.qs.game.model.even.OnErrorEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by zun.wei on 2018/11/21 14:07.
 * Description: 异常报错处理类
 */
@Slf4j
@Component
public class OnErrorEvenHandler implements EvenHandler {


    @Override
    public void handler(Event event) throws Exception {
        OnErrorEvent onErrorEven = (OnErrorEvent) event;
        Throwable error = onErrorEven.getError();
        String sid = onErrorEven.getSid();
        log.error("OnErrorEvenHandler handler ---- 发生错误 sid:{}, message:{}", sid, error.getMessage());
        //error.printStackTrace();
        SpringWebSocketSession springWebSocketSession = onErrorEven.getSpringWebSocketSession();

        WebSocketSender.closeWebSocket(springWebSocketSession);
    }

}
