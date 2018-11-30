package com.qs.game.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.websocket.Session;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
    public void deleteDeadChannels() throws InterruptedException {
        log.debug("SchedulingJobTest =====>>>>>使用cron  {}", System.currentTimeMillis());
        WEB_SOCKET_MAP.forEachValue(10L, webSocketServer ->
        {
            Session session = webSocketServer.getSession();
            if (session.isOpen()) {
                log.debug("SchedulingJobTest deleteDeadChannels -------::{}", webSocketServer.getSid());
                try {
                    webSocketServer.sendTextMessage("HB");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
