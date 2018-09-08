package com.qs.game.cache;

/**
 * Created by zun.wei on 2018/8/4.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface CacheKey {

    String getUserById = "getUserById:";
    String getGoodsBrandById = "getGoodsBrandById:";
    String isLock = "isLock";
    String isClusterLock = "isClusterLock";
    String serverIdKey = "serverIdKey";
    String distributionResult = "distributionResult";
    String redisMqSetPrefix = "redisMqSetPrefix:";
    String redisMqListPrefix = "redisMqListPrefix:";
    String socketUserPrefix = "socket:";
    String tokenPrefix = "token:";
    String userIdPrefix = "userId:";
    String userHB = "userHB:";
    String userKunPool = "userKunPool:";
    String closeGameServer = "closeGameServer";



    enum Redis{
        USER_CENTER_GET_USER_BY_ID(getUserById, "用户中心根据id获取用户"),
        CACHE_PROVIDER_IS_LOCK(isLock, "redis lock是否开启了"),
        CACHE_PROVIDER_IS_CLUSTER_LOCK(isClusterLock, "redis 分布式lock是否开启了"),
        CACHE_PROVIDER_SERVER_ID_KEY(serverIdKey, "mq 服务名缓存"),
        CACHE_PROVIDER_DISTRIBUTION_RESULT(distributionResult, "消息分配结果缓存"),
        CLOSE_GAME_SERVER(closeGameServer, "关闭服务，阻止所有链接"),
        ;

        public String KEY;
        public String COMMENT;
        Redis(String key,String comment) {
            this.KEY = key;
            this.COMMENT = comment;
        }
    }


    enum RedisPrefix{
        REDIS_MQ_SET_PREFIX(redisMqSetPrefix, "redis mq set 消息前缀!"),
        REDIS_MQ_LIST_PREFIX(redisMqListPrefix, "redis mq list 消息前缀!"),
        WEBSOCKET_USER_PREFIX(socketUserPrefix, "websocket 用户前缀!"),
        TOKEN_PREFIX(tokenPrefix, "token前缀!"),
        USER_ID_PREFIX(userIdPrefix, "用户id前缀!"),
        USER_HEART_BEAT(userHB, "websocket client 心跳!"),
        USER_KUN_POOL(userKunPool, "用户鲲池!"),
        ;

        public String KEY;
        public String COMMENT;
        RedisPrefix(String key,String comment) {
            this.KEY = key;
            this.COMMENT = comment;
        }
    }




    enum Memcached{
        USER_CENTER_GET_USER_BY_ID(getUserById, "用户中心根据id获取用户"),
        PRODUCT_CENTER_GET_GOODS_BRAND_BY_ID(getGoodsBrandById, "产品中心根据id获取品牌"),
        ;

        public String KEY;
        public String COMMENT;
        Memcached(String key,String comment) {
            this.KEY = key;
            this.COMMENT = comment;
        }
    }

    enum MemcachedPrefix{

    }

}
