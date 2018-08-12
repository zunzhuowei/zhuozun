package com.qs.game.mq;

import com.qs.game.cache.CacheKey;
import com.qs.game.enum0.RedisMQTopic;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * Created by zun.wei on 2018/8/8 17:55.
 * Description: 消息接收者（消费者）
 */
@Slf4j
@Service
public class Receiver implements MessageListener {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //线程池
    private static final ExecutorService executor = Executors.newFixedThreadPool(20);


    @Override
    public void onMessage(Message message, byte[] pattern) {
        Future future = executor.submit(() -> {
            RedisSerializer<String> valueSerializer = stringRedisTemplate.getStringSerializer();
            String deserialize = valueSerializer.deserialize(message.getBody());
            String p = new String(pattern);
            RedisMQTopic redisMQTopic = null;
            try {
                redisMQTopic = RedisMQTopic.valueOf(p);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                log.error("invalid topic type --::{}", p);
                return null;
            }
            return this.handleMsgRouter(redisMQTopic, deserialize, 5);
        });
        try {
            Object obj = future.get();
            log.error("obj ===::" + obj);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    /**
     * 处理队列消息路由器
     */
    private Object handleMsgRouter(RedisMQTopic redisMQTopic, String message,int tryTimes) throws InterruptedException {
        switch (redisMQTopic) {
            case TOPIC_USERNAME:
            {
                // 高可用时，过滤重复订阅消息
                Long item = stringRedisTemplate.opsForSet().add
                        (this.getMQSetKey(redisMQTopic), message);
                if (Objects.nonNull(item) && item > 0) { //保存成功的，根据业务处理消息
                    //我这里直接存储redis作为测试
                    this.execSysService(redisMQTopic, message, tryTimes);
                }
                log.error("handle mq topic is username --::" + message);
                break;
            }
            case TOPIC_LOGIN_LOG:
            {
                log.error("handle mq topic is login log --::" + message);
                break;
            }
            default:
            {
                log.warn("zhuozun-cache-provider handle message but mq topic not match");
            }
        }
        return message;
    }


    private void execSysService(RedisMQTopic redisMQTopic, String message, int tryTimes) throws InterruptedException {
        Long listItem = stringRedisTemplate.opsForList().leftPush
                (this.getMQListKey(redisMQTopic), message);
        if (Objects.nonNull(listItem) && listItem > 0) {
            //此时如果删除了，由于网络抖动，另外一个订阅服务可能会重复消费。需要定时去删除？
            //stringRedisTemplate.opsForSet().remove(this.getMQSetKey(redisMQTopic), message);
        } else {
            Thread.sleep(2000);
            if (tryTimes > 0)
                execSysService(redisMQTopic, message, --tryTimes);
        }
    }


    private String getMQListKey(RedisMQTopic redisMQTopic) {
        return CacheKey.RedisPrefix.REDIS_MQ_LIST_PREFIX.KEY + redisMQTopic.TOPIC;
    }

    private String getMQSetKey(RedisMQTopic redisMQTopic) {
        return CacheKey.RedisPrefix.REDIS_MQ_SET_PREFIX.KEY + redisMQTopic.TOPIC;
    }


    //如果没有继承 MessageListener 直接写方法名也可以。
    // 但是需要去MessageListenerAdapter 指定处理消息的方法名字
    public void receiveMessage(String message) {
        log.info("收到的mq消息-------222222::" + message);
    }

}
