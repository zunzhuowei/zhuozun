package com.qs.game.utils;

import com.alibaba.fastjson.JSON;
import com.qs.game.cache.CacheKey;
import com.qs.game.constant.StrConst;
import com.qs.game.enum0.RedisMQTopic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by zun.wei on 2018/8/10.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 * mq 服务分发工具，没有完成。
 */
@Component
@Deprecated
public class RedisReceiverUtils {

    private static final String ZERO = "0";
    private static final String ONE = "1";
    private static final String[] ID_LAST = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static final String MASTER = "master";
    private static final String SERVER_KEY = RedisReceiverUtils.createUUid(); //服务key
    private static boolean is_can = false;
    private static String [] RECEIVER_NUMBERS; //接收的位数


    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 接收消息
     * @param message
     * @return
     */
    public String receiverMessage(Message message) throws InterruptedException {
        if (!hasReceiverNumbers() || !is_can) { //如果没有
            is_can = false;
            this.closeReceive(); //关闭接收
            this.delDistribution(); //删除上次服务分配结果
            this.uploadServerKey(SERVER_KEY);//上传server key到redis中
            boolean b = this.getRedisLock(); //获取分布式锁
            if (!b) {
                this.sleep1M(10);//如果没有拿到锁，睡眠10秒钟;等待master重新生成分配再去拿。
                String distribution = this.getDistribution();//新的分配结果
                if (StringUtils.isBlank(distribution)){
                    this.sleep1M(10);
                    receiverMessage(message);
                }
                Map<String,String[]> recuiverNumbers = JSON.parseObject(distribution, Map.class);
                RECEIVER_NUMBERS = recuiverNumbers.get(SERVER_KEY);
            } else {
                this.sleep1M(5);//如果拿到锁，睡眠5秒钟；等待其他server 注册到redis
                List<String> serverKeys = this.getServerKeys();
                List<String> ids = Arrays.asList(ID_LAST);
                String distribution = allotOfAverage(serverKeys, ids); //分配结果
                this.saveDistribution(distribution);//保存分配结果

                Map<String,String[]> recuiverNumbers = JSON.parseObject(distribution, Map.class);
                RECEIVER_NUMBERS = recuiverNumbers.get(SERVER_KEY);
            }
            is_can = true;
        }
        boolean redisCan = this.isCanReceive();


        return null;
    }

    /**
     *  保存分配结果
     * @param distribution 分配结果
     */
    public void saveDistribution(String distribution) {
        stringRedisTemplate.opsForValue().set(CacheKey.Redis.CACHE_PROVIDER_DISTRIBUTION_RESULT.KEY, distribution);
    }

    /**
     *  删除上次服务分配结果
     */
    public void delDistribution() {
        stringRedisTemplate.delete(CacheKey.Redis.CACHE_PROVIDER_DISTRIBUTION_RESULT.KEY);
    }

    /**
     * 获取分配结果
     * @return 重新分配的结果
     */
    public String getDistribution() {
        return stringRedisTemplate.opsForValue().get(CacheKey.Redis.CACHE_PROVIDER_DISTRIBUTION_RESULT.KEY);
    }

    /**
     * 获取接收的尾数
     * @param serverKey 服务key
     * @return 尾数
     */
    public String[] getReceiverNumbersByServerKey(String serverKey) {

        return null;
    }

    /**
     * 是否有接收的位数
     * @return true 有，flase 没有
     */
    public boolean hasReceiverNumbers() {
        return Objects.nonNull(RECEIVER_NUMBERS);
    }


    /**
     * 获取topic列表
     *
     * @return list redisMQTopic
     */
    private List<RedisMQTopic> getRedisMQTopic() {
        return Arrays.asList(RedisMQTopic.values());
    }


    /**
     * 是否可以接受数据
     *
     * @return 是否可以接受
     */
    private boolean isCanReceive() { //0 or null 可以接收，1 不可以接收
        String isCan = stringRedisTemplate.opsForValue().get(CacheKey.Redis.CACHE_PROVIDER_IS_LOCK.KEY);
        return StringUtils.isBlank(isCan) || StringUtils.equals(ZERO, isCan);
    }

    /**
     * 关闭所有服务接收消息
     */
    public void closeReceive() {
        stringRedisTemplate.opsForValue().set(CacheKey.Redis.CACHE_PROVIDER_IS_LOCK.KEY,"1");
    }


    /**
     * 打开所有服务接收消息
     */
    public void openReceive() {
        stringRedisTemplate.delete(CacheKey.Redis.CACHE_PROVIDER_IS_LOCK.KEY);
    }


    /**
     * 获取分布式锁
     *
     * @return 是否拿到锁
     */
    private boolean getRedisLock() {
        return RedisLockUtil.tryGetDistributedLock(redisTemplate,
                CacheKey.Redis.CACHE_PROVIDER_IS_CLUSTER_LOCK.KEY, MASTER, 1000);
    }

    /**
     * 释放分布式锁
     * @return 释放是否成功
     */
    private boolean releaseRedisLock() {
        return RedisLockUtil.releaseDistributedLock(redisTemplate, CacheKey.Redis.CACHE_PROVIDER_IS_CLUSTER_LOCK.KEY, MASTER);
    }

    /**
     * 产生uuid
     *
     * @return uuid
     */
    private static String createUUid() {
        return UUID.randomUUID().toString().replaceAll(StrConst._STR, StrConst.EMPTY_STR);
    }

    /**
     * 线程睡眠一秒钟
     */
    private void sleep1M(int num) throws InterruptedException {
        Thread.sleep(1000 * num);
    }

    /**
     * 服务上传uuid 作为key
     *
     * @param uuidKey uuid
     * @return 是否上传成功
     */
    private boolean uploadServerKey(String uuidKey) {
        Long result = stringRedisTemplate.opsForSet().add(CacheKey.Redis.CACHE_PROVIDER_SERVER_ID_KEY.KEY, uuidKey);
        return Objects.nonNull(result) && result > 0;
    }

    /**
     * 获取server key
     * @return lsit
     */
    public List<String> getServerKeys() {
        Set<String> stringSets = stringRedisTemplate.opsForSet().members(CacheKey.Redis.CACHE_PROVIDER_SERVER_ID_KEY.KEY);
        return Objects.isNull(stringSets) ? new ArrayList<>() : new ArrayList<>(stringSets);
    }

    /**
     * 删除各个服务在reids 中uuid
     * @return 删除是否成功
     */
    private boolean delServerKeyFromCache() {
        Boolean b = stringRedisTemplate.delete(CacheKey.Redis.CACHE_PROVIDER_SERVER_ID_KEY.KEY);
        return Objects.nonNull(b) && b;
    }


    /*
     * 平均分配
     */
    public static String allotOfAverage(List<String> users, List<String> tasks) {
        Map<String, List<String>> allot = new ConcurrentHashMap<>(); //保存分配的信息
        if (Objects.nonNull(users) && users.size() > 0
                && Objects.nonNull(tasks) && tasks.size() > 0) {
            for (int i = 0; i < tasks.size(); i++) {
                int j = i % users.size();
                if (allot.containsKey(users.get(j))) {
                    List<String> list = allot.get(users.get(j));
                    list.add(tasks.get(i));
                    allot.put(users.get(j), list);
                } else {
                    List<String> list = new ArrayList<>();
                    list.add(tasks.get(i));
                    allot.put(users.get(j), list);
                }
            }
        }
        return JSON.toJSONString(allot, true);
    }


    /*
    public static void main(String[] args) {
        String[] userIds = new String[]{"server1", "server2","server3"};
        Map<String, List<String>> result = allotOfAverage(Arrays.asList(userIds),
                Arrays.asList(ID_LAST));
        String json = JSON.toJSONString(result, true);
        System.out.println(json);

        Map m = JSON.parseObject(json, Map.class);
        System.out.println("m.get(\"server1\") = " + m.get("server1"));
    }
    */

}
