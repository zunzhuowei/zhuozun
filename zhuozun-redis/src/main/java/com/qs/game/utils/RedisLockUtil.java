package com.qs.game.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;

/**
 * redis分布式锁.<br>
 * 思路：
 * <pre>
 * 用SETNX命令，SETNX只有在key不存在时才返回成功。这意味着只有一个线程可以成功运行SETNX命令，而其他线程会失败，然后不断重试，直到它们能建立锁。
 * 然后使用脚本来创建锁，因为一个redis脚本同一时刻只能运行一次。
 * 创建锁代码：
 * <code>
 * -- KEYS[1] key,
 * -- ARGV[1] value,
 * -- ARGV[2] expireTimeMilliseconds
 *
 * if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then
 * redis.call('pexpire', KEYS[1], ARGV[2])
 * return 1
 * else
 * return 0
 * end
 * </code>
 * 最后使用脚本来解锁。
 * 解锁代码：
 *
 * <code>
 * -- KEYS[1] key,
 * -- ARGV[1] value
 * if redis.call("get", KEYS[1]) == ARGV[1]
 * then
 * return redis.call("del", KEYS[1])
 * else
 * return 0
 * end
 * </code>
 * </pre>
 */
public class RedisLockUtil {

    private static final Long SUCCESS = 1L;

    // 加锁脚本
    private static final String SCRIPT_LOCK = "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then redis.call('pexpire', KEYS[1], ARGV[2]) return 1 else return 0 end";
    // 解锁脚本
    private static final String SCRIPT_UNLOCK = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    // 加锁脚本sha1值
    private static final String SCRIPT_LOCK_SHA1 = Sha1Util.encrypt(SCRIPT_LOCK);
    // 解锁脚本sha1值
    private static final String SCRIPT_UNLOCK_SHA1 = Sha1Util.encrypt(SCRIPT_UNLOCK);

    /**
     * 尝试获取分布式锁
     *
     * @param redisTemplate          Redis客户端
     * @param lockKey                锁
     * @param requestId              请求标识
     * @param expireTimeMilliseconds 超期时间，多少毫秒后这把锁自动释放
     * @return 返回true表示拿到锁
     */
    @SuppressWarnings("unchecked")
    public static boolean tryGetDistributedLock(@SuppressWarnings("rawtypes") final RedisTemplate redisTemplate,
                                                final String lockKey, final String requestId, final int expireTimeMilliseconds) {

        Object result = redisTemplate.execute(
                new RedisScript<Long>() {
                    @Override
                    public String getSha1() {
                        return SCRIPT_LOCK_SHA1;
                    }

                    @Override
                    public Class<Long> getResultType() {
                        return Long.class;
                    }

                    @Override
                    public String getScriptAsString() {
                        return SCRIPT_LOCK;
                    }

                }, Collections.singletonList(lockKey),// KEYS[1]
                requestId, // ARGV[1]
                String.valueOf(expireTimeMilliseconds) // ARGV[2]
        );

        return SUCCESS.equals(result);
    }

    /**
     * 释放分布式锁
     *
     * @param redisTemplate Redis客户端
     * @param lockKey       锁
     * @param requestId     请求标识
     * @return 返回true表示释放锁成功
     */
    @SuppressWarnings("unchecked")
    public static boolean releaseDistributedLock(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate, String lockKey, String requestId) {

        Object result = redisTemplate.execute(
                new RedisScript<Long>() {
                    @Override
                    public String getSha1() {
                        return SCRIPT_UNLOCK_SHA1;
                    }

                    @Override
                    public Class<Long> getResultType() {
                        return Long.class;
                    }

                    @Override
                    public String getScriptAsString() {
                        return SCRIPT_UNLOCK;
                    }
                }, Collections.singletonList(lockKey), requestId);

        return SUCCESS.equals(result);
    }


}
