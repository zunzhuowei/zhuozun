package com.qs.game.job;

import com.qs.game.handler.spring.WebSocketSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

import static com.qs.game.config.SysConfig.WEB_SOCKET_MAP;

/**
 * Created by zun.wei on 2018/11/28.
 */
@Component
@Slf4j
public class SchedulingJob {


    //心跳包集合；key = mid; value = timestamp
    public static final CopyOnWriteArraySet<String> heartBeats = new CopyOnWriteArraySet<>();

    private static long ONE_SECOND = 1000; //一秒钟


    /**
     * 删除死链接
     */
    //cron = "0/5 * * * * *",
    @Scheduled(fixedDelay = 10000)
    public void webSocketServerHeartBeat() {
        WEB_SOCKET_MAP.forEachValue(100L, springWebSocketSession ->
        {
            try {
                WebSocketSession webSocketSession = springWebSocketSession.getWebSocketSession();
                if (Objects.nonNull(webSocketSession) && !webSocketSession.isOpen()) {
                    WebSocketSender.closeWebSocket(springWebSocketSession);
                } else if (Objects.nonNull(webSocketSession)) {
                    WebSocketSender.sendMessage(springWebSocketSession, new PingMessage());
                }
            } catch (IOException e) {
                //e.printStackTrace();
                try {
                    WebSocketSender.closeWebSocket(springWebSocketSession);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        log.info("now online people count : {}", WEB_SOCKET_MAP.size());

    }

}
