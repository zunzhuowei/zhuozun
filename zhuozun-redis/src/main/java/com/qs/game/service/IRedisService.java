package com.qs.game.service;


import java.util.List;
import java.util.Map;

/**
 * Created by zun.wei on 2018/8/5.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 * <p>
 * base on redisTemplate redis utils service
 */
public interface IRedisService {


    /**
     * 删除缓存
     *
     * @param keys key
     * @return 删除的行数
     */
    long del(final String... keys);

    boolean set(final String key, Object value);

    boolean set(final String key, Object value, long liveTime);

    /**
     * @param key      缓存key
     * @param value    缓存value
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
     *
     * @return 删除成功与否
     */
    boolean flushDB();

    /**
     * 删除redis 所有db 所有 key
     *
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

    /**
     * 添加一个元素到set中
     *
     * @param key      key
     * @param value    value
     * @param liveTime 保存时间（秒）
     * @return 是否保存成功
     */
    Long sAdd(final byte[] key, final byte[] value, final long liveTime);

    /**
     * 添加一个元素到set中
     *
     * @param key      key
     * @param value    value
     * @param liveTime 保存时间（秒）
     * @return 是否保存成功
     */
    Long sAdd(String key, String value, long liveTime);

    /**
     * 添加一个元素到set中
     *
     * @param key   key
     * @param value value
     * @return 是否保存成功
     */
    Long sAdd(String key, String value);

    /**
     * 获取集合的成员数
     *
     * @param key key
     * @return 成员数
     */
    Long sSize(byte[] key);

    /**
     * 获取set集合的成员数
     *
     * @param key key
     * @return 成员数
     */
    Long sSize(String key);

    /**
     * 返回set集合中的所有成员
     *
     * @param key key
     * @return 所有成员
     */
    List<String> sMembers(byte[] key);

    /**
     * 返回set集合中的所有成员
     *
     * @param key key
     * @return 所有成员
     */
    List<String> sMembers(String key);

    /**
     * 移除set并返回集合中的一个随机元素
     *
     * @param key key
     * @return 随机元素
     */
    String sPop(byte[] key);

    /**
     * 移除set并返回集合中的一个随机元素
     *
     * @param key key
     * @return 随机元素
     */
    String sPop(String key);

    /**
     * 返回set集合中一个或多个随机数
     *
     * @param key key
     * @return 随机元素
     */
    String sRandMember(byte[] key);

    /**
     * 返回set集合中一个或多个随机数
     *
     * @param key key
     * @return 随机元素
     */
    String sRandMember(String key);

    /**
     * 返回set集合中一个或多个随机数
     *
     * @param key   key
     * @param count 个数
     * @return 多个随机数
     */
    List<String> sRandMember(byte[] key, long count);

    /**
     * 返回set集合中一个或多个随机数
     *
     * @param key   key
     * @param count 个数
     * @return 多个随机数
     */
    List<String> sRandMember(String key, long count);

    /**
     * 通过索引获取列表中的元素
     * Get element at {@code index} form list at {@code key}.
     *
     * @param key   key
     * @param index index
     * @return element
     */
    String lIndex(byte[] key, long index);

    /**
     * 通过索引获取列表中的元素
     * Get element at {@code index} form list at {@code key}.
     *
     * @param key   key
     * @param index index
     * @return element
     */
    String lIndex(String key, long index);

    /**
     * Get the size of list stored at {@code key}.
     *
     * @param key key
     * @return list size
     */
    Long lLen(byte[] key);

    /**
     * Get the size of list stored at {@code key}.
     *
     * @param key key
     * @return list size
     */
    Long lLen(String key);

    /**
     * Removes and returns first element in list stored at {@code key}.
     *
     * @param key key
     * @return element
     */
    String lPop(byte[] key);

    /**
     * Removes and returns first element in list stored at {@code key}.
     *
     * @param key key
     * @return element
     */
    String lPop(String key);

    /**
     * Removes and returns last element in list stored at {@code key}.
     *
     * @param key key
     * @return element
     */
    String rPop(byte[] key);

    /**
     * Removes and returns last element in list stored at {@code key}.
     *
     * @param key key
     * @return element
     */
    String rPop(String key);

    /**
     * Append {@code values} to {@code key}.
     *
     * @param key    key
     * @param values values
     * @return elements
     */
    Long rPush(byte[] key, byte[]... values);

    /**
     * Append {@code values} to {@code key}.
     *
     * @param key    key
     * @param values values
     * @param liveTime 保存时间（秒）
     * @return elements
     */
    Long rPush(byte[] key, long liveTime, byte[]... values);

    /**
     * Append {@code values} to {@code key}.
     *
     * @param key    key
     * @param values values
     * @return elements
     */
    Long rPush(String key, String... values);

    /**
     * Append {@code values} to {@code key}.
     *
     * @param key    key
     * @param values values
     * @param liveTime 保存时间（秒）
     * @return elements
     */
    Long rPush(String key, long liveTime, String... values);

    /**
     * Prepend {@code values} to {@code key}.
     *
     * @param key    key
     * @param values valus
     * @return elements
     */
    Long lPush(byte[] key, byte[]... values);

    /**
     * Prepend {@code values} to {@code key}.
     *
     * @param key    key
     * @param values valus
     * @return elements
     */
    Long lPush(String key, String... values);

    /**
     * Prepend {@code values} to {@code key}.
     *
     * @param key    key
     * @param values valus
     * @param liveTime 保存时间（秒）
     * @return elements
     */
    Long lPush(byte[] key, long liveTime, byte[]... values);

    /**
     * Prepend {@code values} to {@code key}.
     *
     * @param key    key
     * @param values valus
     * @param liveTime 保存时间（秒）
     * @return elements
     */
    Long lPush(String key, long liveTime, String... values);

}
