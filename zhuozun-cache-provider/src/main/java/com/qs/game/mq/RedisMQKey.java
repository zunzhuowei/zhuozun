package com.qs.game.mq;

import com.qs.game.cache.CacheKey;
import com.qs.game.enum0.RedisMQTopic;

/**
 * Created by zun.wei on 2018/8/14.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *  redis mq 缓存 key 构建
 */
@FunctionalInterface
public interface RedisMQKey {

    enum Type{
        LIST, SET,;
    }

    String buildKey(Type type, RedisMQTopic redisMQTopic);


    static String getKey(RedisMQKey.Type keyType, RedisMQTopic redisMQTopic) {
        RedisMQKey redisMQKey = (type, topic) ->
        {
            String prefixKey = null;
            switch (type) {
                case SET:
                    prefixKey = CacheKey.RedisPrefix.REDIS_MQ_SET_PREFIX.KEY;
                    break;
                case LIST:
                    prefixKey = CacheKey.RedisPrefix.REDIS_MQ_LIST_PREFIX.KEY;
                    break;
                default:
                    prefixKey = "";
            }
            return prefixKey + topic.TOPIC;
        };
        return redisMQKey.buildKey(keyType, redisMQTopic);
    }

}
