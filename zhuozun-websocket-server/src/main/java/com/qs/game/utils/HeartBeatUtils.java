package com.qs.game.utils;

import com.qs.game.common.Global;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
public class HeartBeatUtils {

    private static long oneMinute = 60000; //一分钟

    //心跳包集合；key = mid; value = timestamp
    public static final Map<String, Long> heartBeats = new ConcurrentHashMap<>();


    @Autowired
    private Global global;


    //cron = "0/5 * * * * *",
    @Scheduled(fixedDelay = 5000)
    public void deleteDeadChannels(){
        log.debug("HeartBeatUtils =====>>>>>使用cron  {}",System.currentTimeMillis());
        if (!heartBeats.isEmpty()) {
            long nowTime = new Date().getTime();
            List<Map.Entry<String,Long>> hbs = heartBeats.entrySet().parallelStream()
                    .filter(e -> nowTime - e.getValue() > oneMinute * 2)
                    .collect(Collectors.toList());

            for (Map.Entry<String, Long> hb : hbs) {
                log.info("HeartBeatUtils deleteDeadChannels ---::{}", hb);
                global.closeAndDelSession(hb.getKey()); // 移除session
                heartBeats.remove(hb.getKey()); //移除心跳
            }
        }
    }

    /*
    使用 @Scheduled来创建定时任务 这个注解用来标注一个定时任务方法。
        通过看 @Scheduled源码可以看出它支持多种参数：
    （1）cron：cron表达式，指定任务在特定时间执行；
    （2）fixedDelay：表示上一次任务执行完成后多久再次执行，参数类型为long，单位ms；
    （3）fixedDelayString：与fixedDelay含义一样，只是参数类型变为String；
    （4）fixedRate：表示按一定的频率执行任务，参数类型为long，单位ms；
    （5）fixedRateString: 与fixedRate的含义一样，只是将参数类型变为String；
    （6）initialDelay：表示延迟多久再第一次执行任务，参数类型为long，单位ms；
    （7）initialDelayString：与initialDelay的含义一样，只是将参数类型变为String；
    （8）zone：时区，默认为当前时区，一般没有用到。
     */
}
