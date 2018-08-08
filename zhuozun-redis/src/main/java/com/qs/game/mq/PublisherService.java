package com.qs.game.mq;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zun.wei on 2018/8/8 17:38.
 * Description: 发布服务（生产者）
 */
@Service
public class PublisherService {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    public String sendMessage(String name) {
        try {
            stringRedisTemplate.convertAndSend("TOPIC_USERNAME", name);
            return "消息发送成功了";

        } catch (Exception e) {
            e.printStackTrace();
            return "消息发送失败了";
        }
    }


}
