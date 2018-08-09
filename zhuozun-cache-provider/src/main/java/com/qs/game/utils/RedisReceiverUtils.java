package com.qs.game.utils;

import com.qs.game.cache.CacheKey;
import com.qs.game.enum0.RedisMQTopic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zun.wei on 2018/8/10.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Component
public class RedisReceiverUtils {

    private static final String ZERO = "0";
    private static final String ONE = "1";

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取topic列表
     * @return list redisMQTopic
     */
    private List<RedisMQTopic> getRedisMQTopic() {
        return Arrays.asList(RedisMQTopic.values());
    }


    private boolean isCanReceive() { //0 or null 可以接收，1 不可以接收
        String isCan = stringRedisTemplate.opsForValue().get(CacheKey.Redis.CACHE_PROVIDER_IS_LOCK.KEY);
        return StringUtils.isBlank(isCan) || StringUtils.equals(ZERO, isCan);
    }


    private boolean getRedisLock() {
        return RedisLockUtil.tryGetDistributedLock(redisTemplate,
                CacheKey.Redis.CACHE_PROVIDER_IS_LOCK.KEY, "master", 1000);
    }



}
