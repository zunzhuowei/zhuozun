package com.qs.game.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionCommands;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service
public class RedisService {

    private static String redisCode = "utf-8";

    @Autowired
    public RedisService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private final RedisTemplate redisTemplate;


    /**
     *  删除缓存
     * @param keys key
     * @return 删除的行数
     */
    public long del(final String... keys) {
        RedisCallback<Long> redisCallback = connection -> {
            Long result = 0L;
            for (int i = 0; i < keys.length; i++) {
                result += connection.del(keys[i].getBytes());
            }
            return result;
        };
        return (long) redisTemplate.execute(redisCallback);

    }

    /**
     * @param key 缓存key
     * @param value 缓存value
     * @param liveTime 保存的时间（秒）
     */
    public boolean set(final byte[] key, final byte[] value, final long liveTime) {
        RedisCallback<Boolean> redisCallback = connection -> {
            Boolean b = connection.set(key, value);
            if (liveTime > 0 && Objects.nonNull(b) && b) {
                connection.expire(key, liveTime);
            }
            return b;
        };
        return (boolean) redisTemplate.execute(redisCallback);
    }

    /**
     * @param key 缓存key
     * @param value 缓存value
     * @param liveTime 保存的时间（秒）
     */
    public boolean set(String key, String value, long liveTime) {
        return this.set(key.getBytes(), value.getBytes(), liveTime);
    }

    /**
     * @param key 缓存key
     * @param value 缓存value
     */
    public boolean set(String key, String value) {
        return this.set(key, value, 0L);
    }

    /**
     * @param key 缓存key
     * @param value 缓存value
     */
    public boolean set(byte[] key, byte[] value) {
        return this.set(key, value, 0L);
    }

    /**
     * @param key 缓存key
     * @return 缓存value
     */
    public String get(final String key) {
        RedisCallback<String> redisCallback = connection -> {
            byte [] result = connection.get(key.getBytes());
            try {
                return Objects.isNull(result) ? null : new String(result,redisCode);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return new String(result);
            }
        };
        return (String) redisTemplate.execute(redisCallback);
    }


    /**
     * @param key 缓存key
     * @return 是否存在
     */
    public boolean exists(final String key) {
        RedisCallback<Boolean> redisCallback = connection -> connection.exists(key.getBytes());
        return (boolean) redisTemplate.execute(redisCallback);
    }

    /**
     *  删除redis 当前连接的db 所有 key
     * @return 删除成功与否
     */
    public boolean flushDB() {
        RedisCallback<Boolean> redisCallback = connection -> {
            connection.flushDb();
            return true;
        };
        return (boolean) redisTemplate.execute(redisCallback);
    }

    /**
     *  删除redis 所有db 所有 key
     * @return 删除成功与否
     */
    public boolean flushAll() {
        RedisCallback<Boolean> redisCallback = connection -> {
            connection.flushAll();
            return true;
        };
        return (boolean) redisTemplate.execute(redisCallback);
    }


    /**
     * @return Get the total number of available keys in currently selected database.
     */
    public long dbSize() {
        RedisCallback<Long> redisCallback = RedisServerCommands::dbSize;
        return (long) redisTemplate.execute(redisCallback);
    }

    /**
     * @return Test connection.
     */
    public String ping() {
        RedisCallback<String> redisCallback = RedisConnectionCommands::ping;
        return (String) redisTemplate.execute(redisCallback);
    }

    /**
     * Set the {@code value} of a hash {@code field}.
     * @param key key
     * @param field key field
     * @param value value
     * @return success fail
     */
    public Boolean hSet(String key, String field, String value) {
        RedisCallback<Boolean> redisCallback = connection ->
                connection.hSet(key.getBytes(), field.getBytes(), value.getBytes());
        return (Boolean) redisTemplate.execute(redisCallback);
    }


    /**
     * Set the {@code value} of a hash {@code field}.
     * @param key key
     * @param field key field
     * @param value value
     * @param liveTime 缓存时长（秒）
     * @return success fail
     */
    public Boolean hSet(String key, String field, String value, Long liveTime) {
        RedisCallback<Boolean> redisCallback = connection -> {
            Boolean isSuccess = connection.hSet(key.getBytes(), field.getBytes(), value.getBytes());
            if (liveTime > 0) {
                connection.expire(key.getBytes(), liveTime);
            }
            return isSuccess;
        };
        return (Boolean) redisTemplate.execute(redisCallback);
    }

    /**
     *  get hase value
     * @param key key
     * @param field field
     * @return value
     */
    public String hGet(String key, String field) {
        RedisCallback<String> redisCallback = connection -> {
            byte[] result = connection.hGet(key.getBytes(), field.getBytes());
            if (Objects.isNull(result)) return null;
            if(exists(key)){
                return new String(result);
            }else {
                return null;
            }
        };
        return (String) redisTemplate.execute(redisCallback);
    }

    /**
     *  get all hash value by key
     * @param key key
     * @return all value from key
     */
    public Map<String, String> hGetAll(String key) {
        RedisCallback<Map<String, String>> redisCallback = connection -> {
            if(exists(key)){
                Map<byte[], byte[]> map = connection.hGetAll(key.getBytes());
                Map<String, String> resMap = new HashMap<>();
                if (Objects.isNull(map)) return resMap;
                map.forEach((kkey, value) -> resMap.put(new String(kkey), new String(value)));
                return resMap;
            }else {
                return new HashMap<>();
            }
        };
        return (Map<String, String>) redisTemplate.execute(redisCallback);

    }


}
