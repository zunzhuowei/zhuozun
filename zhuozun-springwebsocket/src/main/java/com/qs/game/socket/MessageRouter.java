package com.qs.game.socket;

import com.qs.game.constant.EvenType;
import com.qs.game.handler.Handler;
import com.qs.game.handler.spring.WebSocketSender;
import com.qs.game.model.even.Event;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

import static com.qs.game.config.SysConfig.ROUTER_POOL_EXECUTOR;

/**
 * Created by zun.wei on 2018/11/21 11:38.
 * Description: 消息路由器
 */
@Slf4j
public class MessageRouter implements Serializable {


    //private static final boolean isSubmit = false; //使用线程池submit
    private static final boolean withCustomProtocol = true; //校验自定义协议

    // 使用线程池的方式，提交消息到线程池执行。
    public static void route(Event event, EvenType evenType) {
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

        try {
            switch (evenType) {
                case ON_OPEN:
                    handler.getOpenEvenHandler().handler(event);
                    break;
                case ON_CLOSE:
                    handler.getCloseEvenHandler().handler(event);
                    break;
                case ON_PONE_MESSAGE:
                    handler.getPongEvenHandler().handler(event);
                    break;
                case ON_ERROR_MESSAGE:
                    handler.getErrorEvenHandler().handler(event);
                    break;
                case ON_STR_MESSAGE: {
                    ROUTER_POOL_EXECUTOR.execute(() ->
                    {
                        try {
                            handler.getStrEvenHandler().handler(event);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    break;
                }
                case ON_BYTE_MESSAGE: {
                    ROUTER_POOL_EXECUTOR.execute(() ->
                    {
                        try {
                            handler.getBinaryEvenHandler().handler(event);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    break;
                }
                default:
                    throw new RuntimeException("no event error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //DisruptorConfig.SocketMessageEventProducer producer = DisruptorConfig.getSocketMessageProducer();
        //producer.onData(new EventEntity().setEvent(event).setEvenType(evenType));
        //if (true) return;
    }

}
