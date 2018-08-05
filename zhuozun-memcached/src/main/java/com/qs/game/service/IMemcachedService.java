package com.qs.game.service;

import net.rubyeye.xmemcached.exception.MemcachedException;

import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by zun.wei on 2018/8/5.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *
 * base on memcachedClient redis utils service
 *
 */
public interface IMemcachedService {

    /**
     *  删除缓存
     * @param keys key
     * @return 删除的行数
     */
    long del(final String... keys) throws InterruptedException, MemcachedException, TimeoutException;

    /**
     * @param key 缓存key
     * @return 缓存value
     */
    String get(final String key) throws InterruptedException, MemcachedException, TimeoutException;


    /**
     * @param key      缓存key
     * @param value    缓存value
     * @param liveTime 保存的时间（秒）
     */
    boolean set(String key, String value, int liveTime) throws InterruptedException, MemcachedException, TimeoutException;

    /**
     * @param key   缓存key
     * @param value 缓存value
     */
    boolean set(String key, String value) throws InterruptedException, MemcachedException, TimeoutException;

    /**
     *  转成json string 存储
     * @param key 缓存key
     * @param value 缓存value
     * @return 是否保存成功
     */
    boolean set(String key, Object value) throws InterruptedException, MemcachedException, TimeoutException;

    /**
     *  转成json string 存储
     * @param key 缓存key
     * @param value 缓存value
     * @param liveTime 保存的时间（秒）
     * @return  是否保存成功
     */
    boolean set(String key, Object value, int liveTime) throws InterruptedException, MemcachedException, TimeoutException;

    /**
     *  转成json array 存储
     * @param key 缓存key
     * @param values 缓存value(多个转json array 存储)
     * @return  是否保存成功
     */
    boolean set(String key, Object... values) throws InterruptedException, MemcachedException, TimeoutException;

    /**
     *  转成json array 存储
     * @param key 缓存key
     * @param liveTime 保存的时间（秒）
     * @param values  缓存value(多个转json array 存储)
     * @return 是否保存成功
     */
    boolean set(String key, int liveTime, Object... values) throws InterruptedException, MemcachedException, TimeoutException;

    /**
     * 插入两条数据
     * @param key1 缓存key1
     * @param value1 缓存value1
     * @param key2 缓存key2
     * @param value2 缓存value2
     * @return 插入成功条数
     */
    int setDouble(String key1, String value1, String key2, String value2) throws InterruptedException, MemcachedException, TimeoutException;

    /**
     * 插入两条数据
     * @param key1 缓存key1
     * @param value1 缓存value1
     * @param key2 缓存key2
     * @param value2 缓存value2
     * @param liveTime  保存的时间（秒）
     * @return 插入成功条数
     */
    int setDouble(String key1, String value1, String key2, String value2, int liveTime) throws InterruptedException, MemcachedException, TimeoutException;

    /**
     * 插入三条数据
     * @param key1 缓存key1
     * @param value1 缓存value1
     * @param key2 缓存key2
     * @param value2 缓存value2
     * @param key3 缓存key3
     * @param value3 缓存value3
     * @return 插入成功条数
     */
    int setThree(String key1, String value1, String key2, String value2, String key3, String value3) throws InterruptedException, MemcachedException, TimeoutException;

    /**
     * 插入三条数据
     * @param key1 缓存key1
     * @param value1 缓存value1
     * @param key2 缓存key2
     * @param value2 缓存value2
     * @param key3 缓存key3
     * @param value3 缓存value3
     * @param liveTime  保存的时间（秒）
     * @return 插入成功条数
     */
    int setThree(String key1, String value1, String key2, String value2, String key3, String value3, int liveTime) throws InterruptedException, MemcachedException, TimeoutException;

    /**
     * 插入多条数据
     * @param keyValue map 的 key value ==> 缓存 的key value
     * @return 插入成功条数
     */
    int setMany(Map<String, String> keyValue) throws InterruptedException, MemcachedException, TimeoutException;

    /**
     * 插入多条数据
     * @param keyValue map 的 key value ==> 缓存 的key value
     * @param liveTime  保存的时间（秒）
     * @return 插入成功条数
     */
    int setMany(Map<String, String> keyValue, int liveTime) throws InterruptedException, MemcachedException, TimeoutException;


    /**
     * @param key 缓存key
     * @return 是否存在
     */
    boolean exists(final String key) throws InterruptedException, MemcachedException, TimeoutException;


    /**
     * 删除memcached 指定port 的db 所有 key
     * @return 删除成功与否
     */
    boolean flushAll() throws InterruptedException, MemcachedException, TimeoutException;


}
