package com.qs.game.utils;

import com.qs.game.cache.CacheKey;
import com.qs.game.common.Global;
import com.qs.game.constant.StrConst;
import com.qs.game.service.ICoreService;
import com.qs.game.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zun.wei on 2018/9/5 19:42.
 *     使用 @Scheduled来创建定时任务 这个注解用来标注一个定时任务方法。
 *         通过看 @Scheduled源码可以看出它支持多种参数：
 *     （1）cron：cron表达式，指定任务在特定时间执行；
 *     （2）fixedDelay：表示上一次任务执行完成后多久再次执行，参数类型为long，单位ms；
 *     （3）fixedDelayString：与fixedDelay含义一样，只是参数类型变为String；
 *     （4）fixedRate：表示按一定的频率执行任务，参数类型为long，单位ms；
 *     （5）fixedRateString: 与fixedRate的含义一样，只是将参数类型变为String；
 *     （6）initialDelay：表示延迟多久再第一次执行任务，参数类型为long，单位ms；
 *     （7）initialDelayString：与initialDelay的含义一样，只是将参数类型变为String；
 *     （8）zone：时区，默认为当前时区，一般没有用到。
 *
 * Description: 定时任务工具类
 */
@Component
@Slf4j
public class SchedulingUtils {

    private static long oneMinute = 60000; //一分钟

    //心跳包集合；key = mid; value = timestamp
    public static final Map<String, Long> heartBeats = new ConcurrentHashMap<>();


    @Autowired
    private Global global;

    @Autowired
    private IRedisService redisService;

    @Autowired
    private ICoreService coreService;


    /**
     * 删除死链接
     */
    //cron = "0/5 * * * * *",
    @Scheduled(fixedDelay = 5000)
    public void deleteDeadChannels() {
        log.debug("HeartBeatUtils =====>>>>>使用cron  {}", System.currentTimeMillis());
        if (!heartBeats.isEmpty()) {
            long nowTime = new Date().getTime();
            heartBeats.entrySet().parallelStream()
                    .filter(e -> nowTime - e.getValue() > oneMinute * 2)
                    .forEach(this::removeSession);
        }
    }

    /**
     * 移除session
     * @param hb key : 游戏id；value ：客户端心跳时间戳
     */
    private void removeSession(Map.Entry<String, Long> hb) {
        log.info("HeartBeatUtils deleteDeadChannels ---::{}", hb);
        global.closeAndDelSession(hb.getKey()); // 移除session
        heartBeats.remove(hb.getKey()); //移除心跳
    }


    /**
     * 停服,把缓存中的玩家数据持久化
     */
    @Scheduled(fixedDelay = 5000)
    public void closeServer() {
        String isClose = redisService.get(CacheKey.Redis.CLOSE_GAME_SERVER.KEY);
        if (StringUtils.equals(StrConst.ONE, isClose) && !Global.getSessionRepo().isEmpty()) {
            //获取在线的所有玩家
            Global.getSessionRepo().forEach((key, context) -> {
                //String mid = context.channel().attr(Global.attrUid).get();
                //存储内存中的玩家数据
                //BusinessThreadUtil.getExecutor().submit(coreService.handlerRemoved(mid));
                //关闭连接的会触发handlerRemoved 的清除玩家内存数据
                context.channel().close();
            });
            //清除所有的玩家session
            Global.getSessionRepo().clear();
        }

    }

}
