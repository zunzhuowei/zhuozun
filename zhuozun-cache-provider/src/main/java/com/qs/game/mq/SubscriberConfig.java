package com.qs.game.mq;

import com.qs.game.enum0.RedisMQTopic;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zun.wei on 2018/8/8 17:58.
 * Description: 消息队列配置
 */
@Configuration
@AutoConfigureAfter({Receiver.class})
public class SubscriberConfig {


    /**
     * 消息监听适配器，注入接受消息方法，输入方法名字 反射方法
     *
     * @param receiver
     * @return
     */
    @Bean
    public MessageListenerAdapter getMessageListenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
        //当没有继承MessageListener时需要写方法名字
    }

    /**
     * 创建消息监听容器
     *
     * @param redisConnectionFactory
     * @param messageListenerAdapter
     * @return
     */
    @Bean
    public RedisMessageListenerContainer getRedisMessageListenerContainer(
            RedisConnectionFactory redisConnectionFactory, MessageListenerAdapter messageListenerAdapter) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        List<Topic> topics = new LinkedList<>();
        topics.add(new PatternTopic(RedisMQTopic.TOPIC_USERNAME.TOPIC));
        topics.add(new PatternTopic(RedisMQTopic.TOPIC_LOGIN_LOG.TOPIC));
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, topics);
        return redisMessageListenerContainer;
    }

}
