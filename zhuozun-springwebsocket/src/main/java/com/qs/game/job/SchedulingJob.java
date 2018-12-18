package com.qs.game.job;

import com.qs.game.enum0.DateEnum;
import com.qs.game.handler.spring.WebSocketSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
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
    @Scheduled(fixedDelay = 3000)
    public void webSocketServerHeartBeat() throws InterruptedException {
        WEB_SOCKET_MAP.forEachValue(10L, springWebSocketSession ->
        {
            // WebSocketSender.sendMessage(springWebSocketSession, new PingMessage());
            heartBeats.add(springWebSocketSession.getSid());
            //WebSocketSender.sendMessage(springWebSocketSession, "HB:" + springWebSocketSession.getSid() + ", online people:" + WEB_SOCKET_MAP.size());
        });
        log.info("now online people count : {}", WEB_SOCKET_MAP.size());

        Thread.sleep(3000);
        heartBeats.forEach(WEB_SOCKET_MAP::remove);
        heartBeats.clear();

        /*if (!heartBeats.isEmpty()) {
            long nowTime = new Date().getTime();
            heartBeats.entrySet().parallelStream()
                    .filter(e -> nowTime - e.getValue() > ONE_SECOND * 3)
                    .forEach(this::removeSession);
        }*/

    }

    /**
     * 移除session
     *
     * @param hb key : 游戏id；value ：客户端心跳时间戳
     */
    private void removeSession(Map.Entry<String, Long> hb) {
        log.info("HeartBeatUtils deleteDeadChannels ---::{},{}", hb
                , DateEnum.YYYY_MM_DD_HH_MM_SS.getDateFormat().format(new Date(hb.getValue())));
        WEB_SOCKET_MAP.remove(hb.getKey()); // 移除session
        heartBeats.remove(hb.getKey()); //移除心跳
    }

}
