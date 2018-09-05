package com.qs.game.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by zun.wei on 2018/9/5 19:42.
 * Description:
 */
@Component
@Slf4j
public class HeartBeatUtils implements Runnable{

    private static long oneMunite = 60000; //一分钟

    public HeartBeatUtils() {
        //new HeartBeatUtils().run();
    }

    public static final Map<String, Long> heartBeats = new ConcurrentHashMap<>();

    @Override
    public void run() {
        if (!heartBeats.isEmpty()) {
            long nowTime = new Date().getTime();
            List<Map.Entry<String,Long>> hbs = heartBeats.entrySet().parallelStream().filter(e -> nowTime - e.getValue() > oneMunite * 2)
            .collect(Collectors.toList());

            //hbs.forEach(e -> e.getKey());

            try {
                Thread.sleep(5000); //睡眠五秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }




}
