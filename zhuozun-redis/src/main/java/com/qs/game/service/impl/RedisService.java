package com.qs.game.service.impl;

import com.alibaba.fastjson.JSON;
import com.qs.game.constant.StrConst;
import com.qs.game.service.IRedisService;
import org.springframework.data.redis.connection.RedisConnectionCommands;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * redis service
 */
@Service
public class RedisService implements IRedisService {


    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;


    @Override
    public long del(final String... keys) {
        RedisCallback<Long> redisCallback = connection -> {
            Long result = 0L;
            for (String key : keys) {
                result += connection.del(key.getBytes());
            }
            return result;
        };
        return (long) redisTemplate.execute(redisCallback);

    }

    @Override
    public boolean set(String key, Object value) {
        String strValue = JSON.toJSONString(value);
        return this.set(key, strValue);
    }

    @Override
    public boolean set(String key, Object value, long liveTime) {
        String strValue = JSON.toJSONString(value);
        return this.set(key, strValue, liveTime);
    }

    @Override
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

    @Override
    public boolean set(String key, String value, long liveTime) {
        return this.set(key.getBytes(), value.getBytes(), liveTime);
    }

    @Override
    public boolean set(String key, String value) {
        return this.set(key, value, 0L);
    }

    @Override
    public boolean set(byte[] key, byte[] value) {
        return this.set(key, value, 0L);
    }

    @Override
    public String get(final String key) {
        RedisCallback<String> redisCallback = connection -> {
            byte[] result = connection.get(key.getBytes());
            try {
                return Objects.isNull(result) ? null : new String(result, StrConst.UTF_8);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return new String(result);
            }
        };
        return (String) redisTemplate.execute(redisCallback);
    }


    @Override
    public boolean exists(final String key) {
        RedisCallback<Boolean> redisCallback = connection -> connection.exists(key.getBytes());
        return (boolean) redisTemplate.execute(redisCallback);
    }

    @Override
    public boolean flushDB() {
        RedisCallback<Boolean> redisCallback = connection -> {
            connection.flushDb();
            return true;
        };
        return (boolean) redisTemplate.execute(redisCallback);
    }

    @Override
    public boolean flushAll() {
        RedisCallback<Boolean> redisCallback = connection -> {
            connection.flushAll();
            return true;
        };
        return (boolean) redisTemplate.execute(redisCallback);
    }


    @Override
    public long dbSize() {
        RedisCallback<Long> redisCallback = RedisServerCommands::dbSize;
        return (long) redisTemplate.execute(redisCallback);
    }

    @Override
    public String ping() {
        RedisCallback<String> redisCallback = RedisConnectionCommands::ping;
        return (String) redisTemplate.execute(redisCallback);
    }

    @Override
    public Boolean hSet(String key, String field, String value) {
        RedisCallback<Boolean> redisCallback = connection ->
                connection.hSet(key.getBytes(), field.getBytes(), value.getBytes());
        return (Boolean) redisTemplate.execute(redisCallback);
    }


    @Override
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

    @Override
    public String hGet(String key, String field) {
        RedisCallback<String> redisCallback = connection -> {
            byte[] result = connection.hGet(key.getBytes(), field.getBytes());
            if (Objects.isNull(result)) return null;
            if (exists(key)) {
                return new String(result);
            } else {
                return null;
            }
        };
        return (String) redisTemplate.execute(redisCallback);
    }

    @Override
    public Map<String, String> hGetAll(String key) {
        RedisCallback<Map<String, String>> redisCallback = connection -> {
            if (exists(key)) {
                Map<byte[], byte[]> map = connection.hGetAll(key.getBytes());
                Map<String, String> resMap = new HashMap<>();
                if (Objects.isNull(map)) return resMap;
                map.forEach((kkey, value) -> resMap.put(new String(kkey), new String(value)));
                return resMap;
            } else {
                return new HashMap<>();
            }
        };
        return (Map<String, String>) redisTemplate.execute(redisCallback);
    }

    @Override
    public Long sAdd(final byte[] key, final byte[] value, final long liveTime) {
        RedisCallback<Long> redisCallback = connection -> {
            Long sAdd = connection.sAdd(key, value);
            if (liveTime > 0 && Objects.nonNull(sAdd) && sAdd > 0) {
                connection.expire(key, liveTime);
            }
            return Objects.isNull(sAdd) ? 0L : sAdd;
        };
        return (Long) redisTemplate.execute(redisCallback);
    }

    @Override
    public Long sAdd(String key, String value, long liveTime) {
        return this.sAdd(key.getBytes(), value.getBytes(), liveTime);
    }

    @Override
    public Long sAdd(String key, String value) {
        return this.sAdd(key, value, 0);
    }

    @Override
    public Long sSize(final byte[] key) {
        RedisCallback<Long> redisCallback = connection -> connection.sCard(key);
        return (Long) redisTemplate.execute(redisCallback);
    }

    @Override
    public Long sSize(String key) {
        return this.sSize(key.getBytes());
    }

    @Override
    public List<String> sMembers(final byte[] key) {
        RedisCallback<List<String>> redisCallback = connection -> {
            Set<byte[]> set = connection.sMembers(key);
            return Objects.isNull(set) ? new ArrayList<>() : set.stream().map(String::new).collect(Collectors.toList());
        };
        return (List<String>) redisTemplate.execute(redisCallback);
    }

    @Override
    public List<String> sMembers(String key) {
        return this.sMembers(key.getBytes());
    }

    @Override
    public String sPop(final byte[] key) {
        RedisCallback<String> redisCallback = connection -> {
            byte[] o = connection.sPop(key);
            return Objects.isNull(o) ? null : new String(o);
        };
        return (String) redisTemplate.execute(redisCallback);
    }

    @Override
    public String sPop(String key) {
        return this.sPop(key.getBytes());
    }


    @Override
    public String sRandMember(final byte[] key) {
        RedisCallback<String> redisCallback = connection -> {
            byte[] o = connection.sRandMember(key);
            return Objects.isNull(o) ? null : new String(o);
        };
        return (String) redisTemplate.execute(redisCallback);
    }

    @Override
    public String sRandMember(String key) {
        return this.sRandMember(key.getBytes());
    }

    @Override
    public List<String> sRandMember(final byte[] key, long count) {
        RedisCallback<List<String>> redisCallback = connection -> {
            List<byte[]> o = connection.sRandMember(key, count);
            return Objects.isNull(o) ? new ArrayList<>() : o.stream().map(String::new).collect(Collectors.toList());
        };
        return (List<String>) redisTemplate.execute(redisCallback);
    }

    @Override
    public List<String> sRandMember(String key, long count) {
        return this.sRandMember(key.getBytes(), count);
    }

    @Override
    public String lIndex(final byte[] key, long index) {
        RedisCallback<String> redisCallback = connection -> {
            byte[] o = connection.lIndex(key, index);
            return Objects.isNull(o) ? null : new String(o);
        };
        return (String) redisTemplate.execute(redisCallback);
    }

    @Override
    public String lIndex(String key, long index) {
        return this.lIndex(key.getBytes(), index);
    }

    @Override
    public Long lLen(final byte[] key) {
        RedisCallback<Long> redisCallback = connection -> {
            Long len = connection.lLen(key);
            return Objects.isNull(len) ? 0L : len;
        };
        return (Long) redisTemplate.execute(redisCallback);
    }

    @Override
    public Long lLen(String key) {
        return this.lLen(key.getBytes());
    }

    @Override
    public String lPop(final byte[] key) {
        RedisCallback<String> redisCallback = connection -> {
            byte[] o = connection.lPop(key);
            return Objects.isNull(o) ? null : new String(o);
        };
        return (String) redisTemplate.execute(redisCallback);
    }

    @Override
    public String lPop(String key) {
        return this.lPop(key.getBytes());
    }

    @Override
    public String rPop(final byte[] key) {
        RedisCallback<String> redisCallback = connection -> {
            byte[] o = connection.rPop(key);
            return Objects.isNull(o) ? null : new String(o);
        };
        return (String) redisTemplate.execute(redisCallback);
    }

    @Override
    public String rPop(String key) {
        return this.rPop(key.getBytes());
    }

    @Override
    public Long rPush(final byte[] key, final byte[]... values) {
        return this.rPush(key, 0L, values);
    }

    @Override
    public Long rPush(final byte[] key, long liveTime, final byte[]... values) {
        RedisCallback<Long> redisCallback = connection -> {
            Long o = connection.rPush(key, values);
            if (liveTime > 0 && Objects.nonNull(o) && o > 0) {
                connection.expire(key, liveTime);
            }
            return Objects.isNull(o) ? 0L : o;
        };
        return (Long) redisTemplate.execute(redisCallback);
    }

    @Override
    public Long rPush(String key, String... values) {
        Long items = 0L;
        for (String value : values) {
            items += this.rPush(key.getBytes(), value.getBytes());
        }
        return items;
    }

    @Override
    public Long rPush(String key, long liveTime, String... values) {
        Long items = 0L;
        for (String value : values) {
            items += this.rPush(key.getBytes(), liveTime, value.getBytes());
        }
        return items;
    }

    @Override
    public Long lPush(final byte[] key, final byte[]... values) {
        return this.lPush(key, 0L, values);
    }

    @Override
    public Long lPush(String key, String... values) {
        Long items = 0L;
        for (String value : values) {
            items += this.lPush(key.getBytes(), value.getBytes());
        }
        return items;
    }

    @Override
    public Long lPush(final byte[] key, long liveTime, final byte[]... values) {
        RedisCallback<Long> redisCallback = connection -> {
            Long o = connection.lPush(key, values);
            if (liveTime > 0 && Objects.nonNull(o) && o > 0) {
                connection.expire(key, liveTime);
            }
            return Objects.isNull(o) ? 0L : o;
        };
        return (Long) redisTemplate.execute(redisCallback);
    }

    @Override
    public Long lPush(String key, long liveTime, String... values) {
        Long items = 0L;
        for (String value : values) {
            items += this.lPush(key.getBytes(),liveTime, value.getBytes());
        }
        return items;
    }

}
