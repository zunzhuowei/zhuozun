package com.qs.game.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.qs.game.constant.StrConst;
import com.qs.game.service.IMemcachedService;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

/**
 * memcached service
 */
@Service
public class MemcachedService implements IMemcachedService {

    private Logger logger = LoggerFactory.getLogger(MemcachedService.class);


    @Resource
    private MemcachedClient memcachedClient;


    @Override
    public long del(String... keys) throws InterruptedException, MemcachedException, TimeoutException {
        long rows = 0;
        for (String key : keys) {
            boolean b = memcachedClient.delete(key);
            if (b) rows += 1;
        }
        return rows;
    }

    @Override
    public String get(String key) throws InterruptedException, MemcachedException, TimeoutException {
        Object value = memcachedClient.get(key);
        return Objects.isNull(value) ? null : value + StrConst.EMPTY_STR;
    }

    @Override
    public boolean set(String key, String value, int liveTime) throws InterruptedException, MemcachedException, TimeoutException {
        return memcachedClient.set(key, liveTime, value);
    }

    @Override
    public boolean set(String key, String value) throws InterruptedException, MemcachedException, TimeoutException {
        return memcachedClient.set(key, 0, value);
    }

    @Override
    public boolean set(String key, Object value) throws InterruptedException, MemcachedException, TimeoutException {
        if (Objects.isNull(value) || Objects.isNull(key)) {
            logger.warn("set memcahed store ---:: key:{},value:{}", key, value);
            return false;
        }
        String jsonStr = JSON.toJSONString(value);
        return memcachedClient.set(key, 0, jsonStr);
    }

    @Override
    public boolean set(String key, Object value, int liveTime) throws InterruptedException, MemcachedException, TimeoutException {
        if (Objects.isNull(value) || Objects.isNull(key)) {
            logger.warn("set with liveTime memcahed store ---:: key:{},value:{}", key, value);
            return false;
        }
        String jsonStr = JSON.toJSONString(value);
        return memcachedClient.set(key, liveTime, jsonStr);
    }

    @Override
    public boolean set(String key, Object... values) throws InterruptedException, MemcachedException, TimeoutException {
        if (Objects.isNull(values) || Objects.isNull(key)) {
            logger.warn("set with liveTime memcahed store ---:: key:{},value:{}", key, values);
            return false;
        }
        String jsonArrStr = JSONArray.toJSONString(values);
        return memcachedClient.set(key, 0, jsonArrStr);
    }

    @Override
    public boolean set(String key, int liveTime, Object... values) throws InterruptedException, MemcachedException, TimeoutException {
        if (Objects.isNull(values) || Objects.isNull(key)) {
            logger.warn("set with liveTime memcahed store ---:: key:{},value:{}", key, values);
            return false;
        }
        String jsonArrStr = JSONArray.toJSONString(values);
        return memcachedClient.set(key, liveTime, jsonArrStr);
    }

    @Override
    public int setDouble(String key1, String value1, String key2, String value2) throws InterruptedException, MemcachedException, TimeoutException {
        return setDouble(key1, value1, key2, value2, 0);
    }

    @Override
    public int setDouble(String key1, String value1, String key2, String value2, int liveTime) throws InterruptedException, MemcachedException, TimeoutException {
        int rows = 0;
        boolean b = memcachedClient.set(key1, liveTime, value1);
        boolean b1 = memcachedClient.set(key2, liveTime, value2);
        if (b) rows += 1;
        if (b1) rows += 1;
        return rows;
    }

    @Override
    public int setThree(String key1, String value1, String key2, String value2, String key3, String value3) throws InterruptedException, MemcachedException, TimeoutException {
        return setThree(key1, value1, key2, value2, key3, value3, 0);
    }

    @Override
    public int setThree(String key1, String value1, String key2, String value2, String key3, String value3, int liveTime) throws InterruptedException, MemcachedException, TimeoutException {
        int rows = 0;
        boolean b = memcachedClient.set(key1, liveTime, value1);
        boolean b1 = memcachedClient.set(key2, liveTime, value2);
        boolean b2 = memcachedClient.set(key2, liveTime, value2);
        if (b) rows += 1;
        if (b1) rows += 1;
        if (b2) rows += 1;
        return rows;
    }

    @Override
    public int setMany(Map<String, String> keyValue) throws InterruptedException, MemcachedException, TimeoutException {
        return setMany(keyValue, 0);
    }

    @Override
    public int setMany(Map<String, String> keyValue, int liveTime) throws InterruptedException, MemcachedException, TimeoutException {
        int rows = 0;
        for (Map.Entry<String, String> entry : keyValue.entrySet()) {
            boolean b = memcachedClient.set(entry.getKey(), liveTime, entry.getValue());
            if (b) rows += 1;
        }
        return rows;
    }


    @Override
    public boolean exists(String key) throws InterruptedException, MemcachedException, TimeoutException {
        Object value = memcachedClient.get(key);
        return !Objects.isNull(value);
    }


    @Override
    public boolean flushAll() {
        try {
            memcachedClient.flushAll();
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
