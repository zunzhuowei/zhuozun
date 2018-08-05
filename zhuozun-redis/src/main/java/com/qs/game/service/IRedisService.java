package com.qs.game.service;


import java.util.Map;

/**
 * Created by zun.wei on 2018/8/5.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *
 *  base on redisTemplate redis utils service
 */
public interface IRedisService {


    /**
     *  删除缓存
     * @param keys key
     * @return 删除的行数
     */
    long del(final String... keys);

    boolean set(final String key, Object value);

    boolean set(final String key, Object value, long liveTime);

    /**
     * @param key 缓存key
     * @param value 缓存value
     * @param liveTime 保存的时间（秒）
     */
    boolean set(final byte[] key, final byte[] value, final long liveTime);

    /**
     * @param key      缓存key
     * @param value    缓存value
     * @param liveTime 保存的时间（秒）
     */
    boolean set(String key, String value, long liveTime);

    /**
     * @param key   缓存key
     * @param value 缓存value
     */
    boolean set(String key, String value);


    /**
     * @param key   缓存key
     * @param value 缓存value
     */
    boolean set(byte[] key, byte[] value);

    /**
     * @param key 缓存key
     * @return 缓存value
     */
    String get(final String key);

    /**
     * @param key 缓存key
     * @return 是否存在
     */
    boolean exists(final String key);

    /**
     * 删除redis 当前连接的db 所有 key
     * @return 删除成功与否
     */
    boolean flushDB();

    /**
     * 删除redis 所有db 所有 key
     * @return 删除成功与否
     */
    boolean flushAll();

    /**
     * @return Get the total number of available keys in currently selected database.
     */
    long dbSize();

    /**
     * @return Test connection.
     */
    String ping();

    /**
     * Set the {@code value} of a hash {@code field}.
     *
     * @param key   key
     * @param field key field
     * @param value value
     * @return success fail
     */
    Boolean hSet(String key, String field, String value);

    /**
     * Set the {@code value} of a hash {@code field}.
     *
     * @param key      key
     * @param field    key field
     * @param value    value
     * @param liveTime 缓存时长（秒）
     * @return success fail
     */
    Boolean hSet(String key, String field, String value, Long liveTime);

    /**
     * get hase value
     *
     * @param key   key
     * @param field field
     * @return value
     */
    String hGet(String key, String field);

    /**
     * get all hash value by key
     *
     * @param key key
     * @return all value from key
     */
    Map<String, String> hGetAll(String key);


}
