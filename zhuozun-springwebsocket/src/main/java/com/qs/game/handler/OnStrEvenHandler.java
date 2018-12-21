package com.qs.game.handler;

import com.qs.game.handler.spring.SpringWebSocketSession;
import com.qs.game.handler.spring.WebSocketSender;
import com.qs.game.model.even.Event;
import com.qs.game.model.even.OnStrEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by zun.wei on 2018/11/21 14:07.
 * Description: 字符串消息处理类
 */
@Slf4j
@Component
public class OnStrEvenHandler implements EvenHandler {


    @Override
    public void handler(Event event) throws Exception  {
        OnStrEvent onStrEven = (OnStrEvent) event;
        String sid = onStrEven.getSid();
        String message = onStrEven.getMessage();
        SpringWebSocketSession springWebSocketSession = onStrEven.getSpringWebSocketSession();

        //WebSocketSender.closeWebSocket(springWebSocketSession);
        log.debug("OnStrEvenHandler handler 收到来自窗口{}的信息:{}", sid, message);
        if (StringUtils.contains(message,"Got ping response")) return;
        if (StringUtils.contains(message,"Returning cached instance")) return;
        if (StringUtils.contains(message,"Processing injected element")) return;
        if (StringUtils.contains(message,"Processing injected")) return;
        if (StringUtils.contains(message,"AbstractValidatingSessionManager")) return;
        System.out.println(message);
    }

}
