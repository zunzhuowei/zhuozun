package com.qs.game.job;

import com.qs.game.handler.spring.SpringWebSocketSession;
import com.qs.game.socket.server.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;

import java.io.IOException;
import java.util.Objects;

import static com.qs.game.config.SysConfig.WEB_SOCKET_MAP;

/**
 * Created by zun.wei on 2018/11/28.
 *
 */
@Component
@Slf4j
public class SchedulingJobTest {


    /**
     * 删除死链接
     */
    //cron = "0/5 * * * * *",
    @Scheduled(fixedDelay = 5000)
    public void webSocketServerHeartBeat() {
        log.debug("SchedulingJobTest =====>>>>>使用cron  {}", System.currentTimeMillis());
        WEB_SOCKET_MAP.forEachValue(10L, webSocket ->
        {
            WebSocketServer webSocketServer = webSocket instanceof WebSocketServer ? ((WebSocketServer) webSocket) : null;
            if (Objects.nonNull(webSocketServer)) {
                Session session = webSocketServer.getSession();
                if (session.isOpen()) {
                    log.debug("SchedulingJobTest tomcat websocket heartbeat -------::{}", webSocketServer.getSid());
                    try {
                        webSocketServer.sendTextMessage("HB");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                SpringWebSocketSession springWebSocketSession = webSocket instanceof SpringWebSocketSession ? ((SpringWebSocketSession) webSocket) : null;
                if (Objects.nonNull(springWebSocketSession)) {
                    WebSocketSession webSocketSession = springWebSocketSession.getWebSocketSession();
                    if (webSocketSession.isOpen()) {
                        log.debug("SchedulingJobTest spring websocket heartbeat -------::{}", springWebSocketSession.getSid());
                        try {
                            webSocketSession.sendMessage(new TextMessage("HB"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        log.info("now online people count : {}", WEB_SOCKET_MAP.size());
    }

}
