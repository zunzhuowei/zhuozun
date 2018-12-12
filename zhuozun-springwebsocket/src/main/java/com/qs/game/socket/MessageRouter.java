package com.qs.game.socket;

import com.qs.game.constant.EvenType;
import com.qs.game.handler.Handler;
import com.qs.game.model.even.Even;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.qs.game.config.SysConfig.ROUTER_POOL_EXECUTOR;
import static com.qs.game.config.SysConfig.WEB_SOCKET_MAP;

/**
 * Created by zun.wei on 2018/11/21 11:38.
 * Description: 消息路由器
 */
@Slf4j
public class MessageRouter implements Serializable {


    private static final boolean isSubmit = false; //使用线程池submit
    private static final boolean withCustomProtocol = true; //校验自定义协议

    // 使用线程池的方式，提交消息到线程池执行。
    public static void route(Even even, EvenType evenType) {
        if (isSubmit) {
            Future future = ROUTER_POOL_EXECUTOR.submit(() ->
            {
                try {
                    MessageRouter.executeRouteMessage(even, evenType, withCustomProtocol);
                } catch (Exception e) {
                    log.warn("MessageRouter route Exception --::{}", e.getMessage());
                    e.printStackTrace();
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
                    MessageRouter.executeRouteMessage(even, evenType, withCustomProtocol);
                } catch (Exception e) {
                    log.warn("MessageRouter route Exception --::{}", e.getMessage());
                    e.printStackTrace();
                }
            });
        }
    }

    private static void executeRouteMessage(Even even, EvenType evenType, boolean withCustomProtocol) throws Exception {
        Handler handler = Handler.getInstance(even, withCustomProtocol);
        if (Objects.isNull(handler)) {
            even.getSysWebSocket().closeWebSocket();
            String sid = even.getSid();
            WEB_SOCKET_MAP.remove(sid);
            log.warn("MessageRouter route executeRouteMessage handler is null,sid:{}", sid);
            return;
        }
        switch (evenType) {
            case ON_OPEN:
                handler.getOpenEvenHandler().handler(even);
                break;
            case ON_CLOSE:
                handler.getCloseEvenHandler().handler(even);
                break;
            case ON_STR_MESSAGE:
                handler.getStrEvenHandler().handler(even);
                break;
            case ON_BYTE_MESSAGE:
                handler.getBinaryEvenHandler().handler(even);
                break;
            case ON_PONE_MESSAGE:
                handler.getPongEvenHandler().handler(even);
                break;
            case ON_ERROR_MESSAGE:
                handler.getErrorEvenHandler().handler(even);
                break;
            default:
                throw new RuntimeException("no even error");
        }
    }

}
