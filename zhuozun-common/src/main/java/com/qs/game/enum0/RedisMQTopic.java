package com.qs.game.enum0;

/**
 * Created by zun.wei on 2018/8/8.
 *  redis mq topic constant
 */
public enum RedisMQTopic {

    TOPIC_USERNAME("TOPIC_USERNAME"),
    TOPIC_LOGIN_LOG("TOPIC_LOGIN_LOG"),
    ;

    public String TOPIC;

    RedisMQTopic(String TOPIC) {
        this.TOPIC = TOPIC;
    }

}
