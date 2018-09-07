package com.qs.game.config;

import com.qs.game.model.sys.Kun;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zun.wei on 2018/9/7 14:56.
 * Description: 初始化数据
 */
@Data
@Slf4j
@Accessors(chain = true)
public class MemoryData implements Serializable {

    //存储鲲池的容量大小
    private static int initUserPoolCapacity = 1000;

    //初始化用户鲲池
    private static final Map<Integer, Kun> INIT_USER_KUN_MAP = new HashMap<>(16); //鲲池

    //用户鲲池内存位置 key:player id;  value:kun pool storage index
    private static final Map<Integer, Integer> USER_KUN_POOL_POSITION = new ConcurrentHashMap<>(20000);

    public static Map<Integer, Integer> getUserKunPoolPosition() {
        return USER_KUN_POOL_POSITION;
    }


    // 用户鲲真正存储的内存
    // key : player id   value : user kun pool
    private static final Map<Integer, Map<Integer, Kun>> user_kun_pool_1 = new ConcurrentHashMap<>(initUserPoolCapacity);
    private static final Map<Integer, Map<Integer, Kun>> user_kun_pool_2 = new ConcurrentHashMap<>(initUserPoolCapacity);
    private static final Map<Integer, Map<Integer, Kun>> user_kun_pool_3 = new ConcurrentHashMap<>(initUserPoolCapacity);
    private static final Map<Integer, Map<Integer, Kun>> user_kun_pool_4 = new ConcurrentHashMap<>(initUserPoolCapacity);
    private static final Map<Integer, Map<Integer, Kun>> user_kun_pool_5 = new ConcurrentHashMap<>(initUserPoolCapacity);
    private static final Map<Integer, Map<Integer, Kun>> user_kun_pool_6 = new ConcurrentHashMap<>(initUserPoolCapacity);
    private static final Map<Integer, Map<Integer, Kun>> user_kun_pool_7 = new ConcurrentHashMap<>(initUserPoolCapacity);
    private static final Map<Integer, Map<Integer, Kun>> user_kun_pool_8 = new ConcurrentHashMap<>(initUserPoolCapacity);
    private static final Map<Integer, Map<Integer, Kun>> user_kun_pool_9 = new ConcurrentHashMap<>(initUserPoolCapacity);
    private static final Map<Integer, Map<Integer, Kun>> user_kun_pool_10 = new ConcurrentHashMap<>(initUserPoolCapacity);
    private static final Map<Integer, Map<Integer, Kun>> user_kun_pool_11 = new ConcurrentHashMap<>(initUserPoolCapacity);
    private static final Map<Integer, Map<Integer, Kun>> user_kun_pool_12 = new ConcurrentHashMap<>(initUserPoolCapacity);
    private static final Map<Integer, Map<Integer, Kun>> user_kun_pool_13 = new ConcurrentHashMap<>(initUserPoolCapacity);
    private static final Map<Integer, Map<Integer, Kun>> user_kun_pool_14 = new ConcurrentHashMap<>(initUserPoolCapacity);
    private static final Map<Integer, Map<Integer, Kun>> user_kun_pool_15 = new ConcurrentHashMap<>(initUserPoolCapacity);
    private static final Map<Integer, Map<Integer, Kun>> user_kun_pool_16 = new ConcurrentHashMap<>(initUserPoolCapacity);
    private static final Map<Integer, Map<Integer, Kun>> user_kun_pool_17 = new ConcurrentHashMap<>(initUserPoolCapacity);
    private static final Map<Integer, Map<Integer, Kun>> user_kun_pool_18 = new ConcurrentHashMap<>(initUserPoolCapacity);
    private static final Map<Integer, Map<Integer, Kun>> user_kun_pool_19 = new ConcurrentHashMap<>(initUserPoolCapacity);
    private static final Map<Integer, Map<Integer, Kun>> user_kun_pool_20 = new ConcurrentHashMap<>(initUserPoolCapacity);



    // 获取存储下标
    public static Integer getStorageIndex() {
        return user_kun_pool_1.size() < initUserPoolCapacity ? 1 :
                user_kun_pool_2.size() < initUserPoolCapacity ? 2 :
                user_kun_pool_3.size() < initUserPoolCapacity ? 3 :
                user_kun_pool_4.size() < initUserPoolCapacity ? 4 :
                user_kun_pool_5.size() < initUserPoolCapacity ? 5 :
                user_kun_pool_6.size() < initUserPoolCapacity ? 6 :
                user_kun_pool_7.size() < initUserPoolCapacity ? 7 :
                user_kun_pool_8.size() < initUserPoolCapacity ? 8 :
                user_kun_pool_9.size() < initUserPoolCapacity ? 9 :
                user_kun_pool_10.size() < initUserPoolCapacity ? 10 :
                user_kun_pool_11.size() < initUserPoolCapacity ? 11 :
                user_kun_pool_12.size() < initUserPoolCapacity ? 12 :
                user_kun_pool_13.size() < initUserPoolCapacity ? 13 :
                user_kun_pool_14.size() < initUserPoolCapacity ? 14 :
                user_kun_pool_15.size() < initUserPoolCapacity ? 15 :
                user_kun_pool_16.size() < initUserPoolCapacity ? 16 :
                user_kun_pool_17.size() < initUserPoolCapacity ? 17 :
                user_kun_pool_18.size() < initUserPoolCapacity ? 18 :
                user_kun_pool_19.size() < initUserPoolCapacity ? 19 :
                user_kun_pool_20.size() < initUserPoolCapacity ? 20 : 0;

    }

    // 获取真正存储的位置
    public static Map<Integer, Map<Integer, Kun>> getKunStorageByIndex(Integer storageIndex) {
        switch (storageIndex) {
            case 1:
                return user_kun_pool_1;
            case 2:
                return user_kun_pool_2;
            case 3:
                return user_kun_pool_3;
            case 4:
                return user_kun_pool_4;
            case 5:
                return user_kun_pool_5;
            case 6:
                return user_kun_pool_6;
            case 7:
                return user_kun_pool_7;
            case 8:
                return user_kun_pool_8;
            case 9:
                return user_kun_pool_9;
            case 10:
                return user_kun_pool_10;
            case 11:
                return user_kun_pool_11;
            case 12:
                return user_kun_pool_12;
            case 13:
                return user_kun_pool_13;
            case 14:
                return user_kun_pool_14;
            case 15:
                return user_kun_pool_15;
            case 16:
                return user_kun_pool_16;
            case 17:
                return user_kun_pool_17;
            case 18:
                return user_kun_pool_18;
            case 19:
                return user_kun_pool_19;
            case 20:
                return user_kun_pool_20;
            default:
                return user_kun_pool_20;
        }
    }

    static {
        //初始化鲲池
        INIT_USER_KUN_MAP.put(1, new Kun().setGold(1).setPosition(0).setTimeStamp(0).setType(1));
        INIT_USER_KUN_MAP.put(2, new Kun().setGold(1).setPosition(0).setTimeStamp(0).setType(1));
        INIT_USER_KUN_MAP.put(3, new Kun().setGold(1).setPosition(0).setTimeStamp(0).setType(1));
        INIT_USER_KUN_MAP.put(4, new Kun().setGold(0).setPosition(0).setTimeStamp(0).setType(0));
        INIT_USER_KUN_MAP.put(5, new Kun().setGold(0).setPosition(0).setTimeStamp(0).setType(0));
        INIT_USER_KUN_MAP.put(6, new Kun().setGold(0).setPosition(0).setTimeStamp(0).setType(0));
        INIT_USER_KUN_MAP.put(7, new Kun().setGold(0).setPosition(0).setTimeStamp(0).setType(0));
        INIT_USER_KUN_MAP.put(8, new Kun().setGold(0).setPosition(0).setTimeStamp(0).setType(0));
        INIT_USER_KUN_MAP.put(9, new Kun().setGold(0).setPosition(0).setTimeStamp(0).setType(0));
        INIT_USER_KUN_MAP.put(10, new Kun().setGold(0).setPosition(0).setTimeStamp(0).setType(0));
        INIT_USER_KUN_MAP.put(11, new Kun().setGold(0).setPosition(0).setTimeStamp(0).setType(0));
        INIT_USER_KUN_MAP.put(12, new Kun().setGold(0).setPosition(0).setTimeStamp(0).setType(0));
        INIT_USER_KUN_MAP.put(13, new Kun().setGold(0).setPosition(0).setTimeStamp(0).setType(0));
        INIT_USER_KUN_MAP.put(14, new Kun().setGold(0).setPosition(0).setTimeStamp(0).setType(0));
        INIT_USER_KUN_MAP.put(15, new Kun().setGold(0).setPosition(0).setTimeStamp(0).setType(0));
        INIT_USER_KUN_MAP.put(16, new Kun().setGold(0).setPosition(0).setTimeStamp(0).setType(0));
    }

    //获取用户初始化鲲池
    public static Map<Integer, Kun> getInitUserKunMap() {
        return INIT_USER_KUN_MAP;
    }


}
