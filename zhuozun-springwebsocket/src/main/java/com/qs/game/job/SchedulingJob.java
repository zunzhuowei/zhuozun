package com.qs.game.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
    @Scheduled(fixedDelay = 5000)
    public void webSocketServerHeartBeat() {
        WEB_SOCKET_MAP.forEachValue(10L, webSocket ->
        {
            try {
                webSocket.sendMessage("HB:" + webSocket.getSid() + ", online people:" + WEB_SOCKET_MAP.size());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        log.info("now online people count : {}", WEB_SOCKET_MAP.size());
    }

}
