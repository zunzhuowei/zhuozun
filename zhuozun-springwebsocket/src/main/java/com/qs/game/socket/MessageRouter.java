package com.qs.game.socket;

import com.qs.game.constant.EvenType;
import com.qs.game.handler.Handler;
import com.qs.game.handler.spring.SpringWebSocketSession;
import com.qs.game.handler.spring.WebSocketSender;
import com.qs.game.model.even.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.qs.game.config.SysConfig.ROUTER_POOL_EXECUTOR;
import static com.qs.game.config.SysConfig.SID;

/**
 * Created by zun.wei on 2018/11/21 11:38.
 * Description: 消息路由器
 */
@Slf4j
public class MessageRouter implements Serializable {


    private static final boolean isSubmit = false; //使用线程池submit
    private static final boolean withCustomProtocol = true; //校验自定义协议

    // 使用线程池的方式，提交消息到线程池执行。
    public static void route(Event event, EvenType evenType) {
        String sid = event.getSid();

        //DisruptorConfig.SocketMessageEventProducer producer = DisruptorConfig.getSocketMessageProducer();
        //producer.onData(new EventEntity().setEvent(event).setEvenType(evenType));
        //if (true) return;

        if (isSubmit) {
            Future future = ROUTER_POOL_EXECUTOR.submit(() ->
            {
                try {
                    MessageRouter.executeRouteMessage(event, evenType, withCustomProtocol);
                } catch (Exception e) {
                    log.warn("MessageRouter route Exception 1 --::{}", e.getMessage());
                    try {
                        WebSocketSender.closeWebSocket(event.getSpringWebSocketSession());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    //e.printStackTrace();
                }
            });
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        else {
            ROUTER_POOL_EXECUTOR.execute(() ->
            {
                try {
                    MessageRouter.executeRouteMessage(event, evenType, withCustomProtocol);
                } catch (Exception e) {
                    log.warn("MessageRouter route Exception 2 --::{}", e.getMessage());
                    try {
                        WebSocketSender.closeWebSocket(event.getSpringWebSocketSession());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    //e.printStackTrace();
                }
            });
        }
    }

    private static void executeRouteMessage(Event event, EvenType evenType, boolean withCustomProtocol) throws Exception {

        Handler handler = Handler.getInstance(event, withCustomProtocol);
        String sid = event.getSid();
        if (Objects.isNull(handler)) {
            try {
                WebSocketSender.closeWebSocket(event.getSpringWebSocketSession());
            } catch (IOException e) {
                log.warn("MessageRouter route executeRouteMessage handler closeWebSocket", e.getMessage());
                //e.printStackTrace();
            }
            log.warn("MessageRouter route executeRouteMessage handler is null,sid:{}", sid);
            return;
        }

        switch (evenType) {
            case ON_OPEN:
                handler.getOpenEvenHandler().handler(event);
                break;
            case ON_CLOSE:
                handler.getCloseEvenHandler().handler(event);
                break;
            case ON_STR_MESSAGE: {
                handler.getStrEvenHandler().handler(event);
                break;
            }
            case ON_BYTE_MESSAGE: {
                handler.getBinaryEvenHandler().handler(event);
                break;
            }
            case ON_PONE_MESSAGE:
                handler.getPongEvenHandler().handler(event);
                break;
            case ON_ERROR_MESSAGE:
                handler.getErrorEvenHandler().handler(event);
                break;
            default:
                throw new RuntimeException("no event error");
        }
    }

}
