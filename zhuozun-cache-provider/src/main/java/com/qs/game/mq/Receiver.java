package com.qs.game.mq;

import com.qs.game.enum0.RedisMQTopic;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
            RedisMQTopic redisMQTopic = RedisMQTopic.valueOf(p);
            return Receiver.handleMsgRouter(redisMQTopic, deserialize);
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
    private static Object handleMsgRouter(RedisMQTopic redisMQTopic, String message) {
        switch (redisMQTopic) {
            case TOPIC_USERNAME:
            {
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


    //如果没有继承 MessageListener 直接写方法名也可以。
    // 但是需要去MessageListenerAdapter 指定处理消息的方法名字
    public void receiveMessage(String message) {
        log.info("收到的mq消息-------222222::" + message);
    }

}
