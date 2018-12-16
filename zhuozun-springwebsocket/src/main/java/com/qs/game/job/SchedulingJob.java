package com.qs.game.job;

import com.qs.game.enum0.DateEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.qs.game.config.SysConfig.WEB_SOCKET_MAP;

/**
 * Created by zun.wei on 2018/11/28.
 *
 */
@Component
@Slf4j
public class SchedulingJob {


    //心跳包集合；key = mid; value = timestamp
    public static final Map<String, Long> heartBeats = new ConcurrentHashMap<>(102400);

    private static long ONE_SECOND = 1000; //一秒钟


    /**
     * 删除死链接
     */
    //cron = "0/5 * * * * *",
    @Scheduled(fixedDelay = 3000)
    public void webSocketServerHeartBeat() {
        /*WEB_SOCKET_MAP.forEachValue(10L, webSocket ->
        {
            try {
                WebSocketSession webSocketSession = webSocket.getWebSocketSession();
                Map<String,Object> attrs = webSocketSession.getAttributes();
                webSocket.sendMessage("HB:" + attrs.get("sid") + ", online people:" + WEB_SOCKET_MAP.size());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });*/
        log.info("now online people count : {}", WEB_SOCKET_MAP.size());

        /*if (!heartBeats.isEmpty()) {
            long nowTime = new Date().getTime();
            heartBeats.entrySet().parallelStream()
                    .filter(e -> nowTime - e.getValue() > ONE_SECOND * 3)
                    .forEach(this::removeSession);
        }*/

    }

    /**
     * 移除session
     * @param hb key : 游戏id；value ：客户端心跳时间戳
     */
    private void removeSession(Map.Entry<String, Long> hb) {
        log.info("HeartBeatUtils deleteDeadChannels ---::{},{}", hb
                , DateEnum.YYYY_MM_DD_HH_MM_SS.getDateFormat().format(new Date(hb.getValue())));
        WEB_SOCKET_MAP.remove(hb.getKey()); // 移除session
        heartBeats.remove(hb.getKey()); //移除心跳
    }

}
