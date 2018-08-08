package com.qs.game.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zun.wei on 2018/8/8 17:55.
 * Description: 消息接收者（消费者）
 */
@Slf4j
@Service
public class Receiver implements MessageListener {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        RedisSerializer<String> valueSerializer = stringRedisTemplate.getStringSerializer();
        String deserialize = valueSerializer.deserialize(message.getBody());
        String p = new String(pattern);
        if (StringUtils.equals("TOPIC_USERNAME", p)) {
            log.error("pattern ------- is true ------::");
        }
        log.info("收到的mq消息-------extend from MessageListener::" + deserialize);
    }

    public void receiveMessage(String message) {
        log.info("收到的mq消息-------222222::" + message);
    }

}
