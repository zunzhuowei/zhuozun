package com.qs.game.mq;

import com.qs.game.enum0.RedisMQTopic;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zun.wei on 2018/8/8 17:38.
 * Description: 发布服务（生产者）
 */
@Service
public class PublisherService {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    private static final String MSG_ID = "msgId:";


    /**
     *  发送用户名
     */
    public MessageMQ sendUserName(String username) {
        return this.send2RedisMqQueue(username, RedisMQTopic.TOPIC_USERNAME);
    }


    /**
     *  发送用户日志
     */
    public MessageMQ sendUserLog(String userLog) {
        return this.send2RedisMqQueue(userLog, RedisMQTopic.TOPIC_LOGIN_LOG);
    }


    /**
     *  发送到redis mq 队列中
     * @param message 发送的消息内容
     * @param redisMQTopic 消息主题
     * @return MessageMQ包装类
     */
    private MessageMQ send2RedisMqQueue(String message, RedisMQTopic redisMQTopic) {
        MessageMQ msg = new MessageMQ();
        try {
            Long msgId = stringRedisTemplate.opsForValue()
                    .increment(MSG_ID + redisMQTopic.TOPIC, 1);
            msg.setMsgId(msgId).setContent(message).setSuccess(true);
            //stringRedisTemplate.opsForList().le
            stringRedisTemplate.convertAndSend(redisMQTopic.TOPIC, msg);
            return msg;
        } catch (Exception e) {
            e.printStackTrace();
            msg.setSuccess(false);
            return msg;
        }
    }

    private List<String> set2MemoryTemp(RedisMQTopic redisMQTopic) {
        //stringRedisTemplate.opsForList().
        return null;
    }

}
