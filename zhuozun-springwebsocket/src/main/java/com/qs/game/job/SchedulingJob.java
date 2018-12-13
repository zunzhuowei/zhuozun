package com.qs.game.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

import static com.qs.game.config.SysConfig.WEB_SOCKET_MAP;

/**
 * Created by zun.wei on 2018/11/28.
 *
 */
@Component
@Slf4j
public class SchedulingJob {


    /**
     * 删除死链接
     */
    //cron = "0/5 * * * * *",
    //@Scheduled(fixedDelay = 5000)
    public void webSocketServerHeartBeat() {
        WEB_SOCKET_MAP.forEachValue(10L, webSocket ->
        {
            try {
                WebSocketSession webSocketSession = webSocket.getWebSocketSession();
                Map<String,Object> attrs = webSocketSession.getAttributes();
                webSocket.sendMessage("HB:" + attrs.get("sid") + ", online people:" + WEB_SOCKET_MAP.size());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        log.info("now online people count : {}", WEB_SOCKET_MAP.size());
    }

}
