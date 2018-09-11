package com.qs.game.config.game;

import com.qs.game.common.game.KunType;
import com.qs.game.model.game.Kuns;
import com.qs.game.model.game.Pool;
import com.qs.game.model.game.PoolCell;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 游戏管理者，管理游戏相关业务
 */
public interface GameManager {

    //初始化用户鲲池
    //Map<Integer, Kuns> INIT_KUN_POOL = new HashMap<>(16); //鲲池
    Pool POOL = new Pool();


    //获取用户初始化鲲池
    default Pool getInitKunPool() {
        if (Objects.isNull(POOL.getPoolCells()) || POOL.getPoolCells().isEmpty()) {
            List<PoolCell> cells = new LinkedList<>();
            //初始化鲲池
            cells.add(new PoolCell().setNo(0).setKuns(new Kuns().setWork(0).setTime(0).setType(KunType.TYPE_1)));
            cells.add(new PoolCell().setNo(1).setKuns(new Kuns().setWork(0).setTime(0).setType(KunType.TYPE_1)));
            cells.add(new PoolCell().setNo(2).setKuns(new Kuns().setWork(0).setTime(0).setType(KunType.TYPE_1)));
//            cells.add(new PoolCell().setNo(3).setKuns(new Kuns().setWork(0).setTime(0).setType(0)));
//            cells.add(new PoolCell().setNo(4).setKuns(new Kuns().setWork(0).setTime(0).setType(0)));
//            cells.add(new PoolCell().setNo(5).setKuns(new Kuns().setWork(0).setTime(0).setType(0)));
//            cells.add(new PoolCell().setNo(6).setKuns(new Kuns().setWork(0).setTime(0).setType(0)));
//            cells.add(new PoolCell().setNo(7).setKuns(new Kuns().setWork(0).setTime(0).setType(0)));
//            cells.add(new PoolCell().setNo(8).setKuns(new Kuns().setWork(0).setTime(0).setType(0)));
//            cells.add(new PoolCell().setNo(9).setKuns(new Kuns().setWork(0).setTime(0).setType(0)));
//            cells.add(new PoolCell().setNo(10).setKuns(new Kuns().setWork(0).setTime(0).setType(0)));
//            cells.add(new PoolCell().setNo(11).setKuns(new Kuns().setWork(0).setTime(0).setType(0)));
//            cells.add(new PoolCell().setNo(12).setKuns(new Kuns().setWork(0).setTime(0).setType(0)));
//            cells.add(new PoolCell().setNo(13).setKuns(new Kuns().setWork(0).setTime(0).setType(0)));
//            cells.add(new PoolCell().setNo(14).setKuns(new Kuns().setWork(0).setTime(0).setType(0)));
//            cells.add(new PoolCell().setNo(15).setKuns(new Kuns().setWork(0).setTime(0).setType(0)));
            POOL.setPoolCells(cells);
        }
        return POOL;
    }




    //存储鲲池的容量大小
    int POOL_STORAGE_CAPACITY = 1000;

    //用户鲲池内存位置 key:player id;  value:kun pool storage index
     Map<Integer, Integer> USER_KUN_POOL_POSITION = new ConcurrentHashMap<>(20000);


    // 用户鲲真正存储的内存
    // key : player id   value : user kun pool
     Map<String, Pool> KUN_POOL_STORAGE_1 = new ConcurrentHashMap<>(POOL_STORAGE_CAPACITY);
     Map<String, Pool> KUN_POOL_STORAGE_2 = new ConcurrentHashMap<>(POOL_STORAGE_CAPACITY);
     Map<String, Pool> KUN_POOL_STORAGE_3 = new ConcurrentHashMap<>(POOL_STORAGE_CAPACITY);
     Map<String, Pool> KUN_POOL_STORAGE_4 = new ConcurrentHashMap<>(POOL_STORAGE_CAPACITY);
     Map<String, Pool> KUN_POOL_STORAGE_5 = new ConcurrentHashMap<>(POOL_STORAGE_CAPACITY);
     Map<String, Pool> KUN_POOL_STORAGE_6 = new ConcurrentHashMap<>(POOL_STORAGE_CAPACITY);
     Map<String, Pool>  KUN_POOL_STORAGE_7 = new ConcurrentHashMap<>(POOL_STORAGE_CAPACITY);
     Map<String, Pool>  KUN_POOL_STORAGE_8 = new ConcurrentHashMap<>(POOL_STORAGE_CAPACITY);
     Map<String, Pool>  KUN_POOL_STORAGE_9 = new ConcurrentHashMap<>(POOL_STORAGE_CAPACITY);
     Map<String, Pool>  KUN_POOL_STORAGE_10 = new ConcurrentHashMap<>(POOL_STORAGE_CAPACITY);
     Map<String, Pool>  KUN_POOL_STORAGE_11 = new ConcurrentHashMap<>(POOL_STORAGE_CAPACITY);
     Map<String, Pool>  KUN_POOL_STORAGE_12 = new ConcurrentHashMap<>(POOL_STORAGE_CAPACITY);
     Map<String, Pool>  KUN_POOL_STORAGE_13 = new ConcurrentHashMap<>(POOL_STORAGE_CAPACITY);
     Map<String, Pool>  KUN_POOL_STORAGE_14 = new ConcurrentHashMap<>(POOL_STORAGE_CAPACITY);
     Map<String, Pool>  KUN_POOL_STORAGE_15 = new ConcurrentHashMap<>(POOL_STORAGE_CAPACITY);
     Map<String, Pool>  KUN_POOL_STORAGE_16 = new ConcurrentHashMap<>(POOL_STORAGE_CAPACITY);
     Map<String, Pool>  KUN_POOL_STORAGE_17 = new ConcurrentHashMap<>(POOL_STORAGE_CAPACITY);
     Map<String, Pool>  KUN_POOL_STORAGE_18 = new ConcurrentHashMap<>(POOL_STORAGE_CAPACITY);
     Map<String, Pool>  KUN_POOL_STORAGE_19 = new ConcurrentHashMap<>(POOL_STORAGE_CAPACITY);
     Map<String, Pool>  KUN_POOL_STORAGE_20 = new ConcurrentHashMap<>(POOL_STORAGE_CAPACITY);


    default Map<Integer, Integer> getUserKunPoolPosition() {
        return USER_KUN_POOL_POSITION;
    }


    // 获取存储下标
    default Integer getStorageIndex() {
        return KUN_POOL_STORAGE_1.size() < POOL_STORAGE_CAPACITY ? 1 :
                KUN_POOL_STORAGE_2.size() < POOL_STORAGE_CAPACITY ? 2 :
                KUN_POOL_STORAGE_3.size() < POOL_STORAGE_CAPACITY ? 3 :
                KUN_POOL_STORAGE_4.size() < POOL_STORAGE_CAPACITY ? 4 :
                KUN_POOL_STORAGE_5.size() < POOL_STORAGE_CAPACITY ? 5 :
                KUN_POOL_STORAGE_6.size() < POOL_STORAGE_CAPACITY ? 6 :
                KUN_POOL_STORAGE_7.size() < POOL_STORAGE_CAPACITY ? 7 :
                KUN_POOL_STORAGE_8.size() < POOL_STORAGE_CAPACITY ? 8 :
                KUN_POOL_STORAGE_9.size() < POOL_STORAGE_CAPACITY ? 9 :
                KUN_POOL_STORAGE_10.size() < POOL_STORAGE_CAPACITY ? 10 :
                KUN_POOL_STORAGE_11.size() < POOL_STORAGE_CAPACITY ? 11 :
                KUN_POOL_STORAGE_12.size() < POOL_STORAGE_CAPACITY ? 12 :
                KUN_POOL_STORAGE_13.size() < POOL_STORAGE_CAPACITY ? 13 :
                KUN_POOL_STORAGE_14.size() < POOL_STORAGE_CAPACITY ? 14 :
                KUN_POOL_STORAGE_15.size() < POOL_STORAGE_CAPACITY ? 15 :
                KUN_POOL_STORAGE_16.size() < POOL_STORAGE_CAPACITY ? 16 :
                KUN_POOL_STORAGE_17.size() < POOL_STORAGE_CAPACITY ? 17 :
                KUN_POOL_STORAGE_18.size() < POOL_STORAGE_CAPACITY ? 18 :
                KUN_POOL_STORAGE_19.size() < POOL_STORAGE_CAPACITY ? 19 :
                KUN_POOL_STORAGE_20.size() < POOL_STORAGE_CAPACITY ? 20 : 0;
    }

    // 获取真正存储的位置
    default Map<String, Pool> getKunStorageByIndex(Integer storageIndex) {
        switch (storageIndex) {
            case 1:
                return KUN_POOL_STORAGE_1;
            case 2:
                return KUN_POOL_STORAGE_2;
            case 3:
                return KUN_POOL_STORAGE_3;
            case 4:
                return KUN_POOL_STORAGE_4;
            case 5:
                return KUN_POOL_STORAGE_5;
            case 6:
                return KUN_POOL_STORAGE_6;
            case 7:
                return KUN_POOL_STORAGE_7;
            case 8:
                return KUN_POOL_STORAGE_8;
            case 9:
                return KUN_POOL_STORAGE_9;
            case 10:
                return KUN_POOL_STORAGE_10;
            case 11:
                return KUN_POOL_STORAGE_11;
            case 12:
                return KUN_POOL_STORAGE_12;
            case 13:
                return KUN_POOL_STORAGE_13;
            case 14:
                return KUN_POOL_STORAGE_14;
            case 15:
                return KUN_POOL_STORAGE_15;
            case 16:
                return KUN_POOL_STORAGE_16;
            case 17:
                return KUN_POOL_STORAGE_17;
            case 18:
                return KUN_POOL_STORAGE_18;
            case 19:
                return KUN_POOL_STORAGE_19;
            case 20:
                return KUN_POOL_STORAGE_20;
            default:
                return KUN_POOL_STORAGE_20;
        }
    }


    /**
     *  获取内存中玩家对应的鲲池
     * @param mid 玩家id
     * @param index 存储鲲池的storage 的下标
     * @return 玩家鲲池
     */
    default Pool getMemoryKunPool(String mid, Integer index) {
        Map<String, Pool> storage = this.getKunStorageByIndex(index);
        return storage.get(mid);
    }

    /**
     * 获取内存中玩家对应的鲲池
     * @param mid 玩家id
     * @param index 存储鲲池的storage 的下标
     */
    default void removeMemoryKunPool(String mid, Integer index) {
        //根据索引下标获取存储玩家鲲池库
        Map<String, Pool> storage = this.getKunStorageByIndex(index);
        //移除玩家鲲池在缓存中的存储
        storage.remove(mid);
        //移除鲲存储索引表
        this.getUserKunPoolPosition().remove(Integer.valueOf(mid));
    }


    /**
     * 保存到内存中
     * @param mid 玩家mid
     * @param pool 玩家对应的鲲池
     */
    default void storageOnMemory(String mid, Pool pool) {
        //获取内存存储位置下标
        Integer index = this.getStorageIndex();
        //设置用户对应的存储下标
        this.getUserKunPoolPosition().put(Integer.valueOf(mid), index);
        //获取存储单元
        Map<String, Pool> storage = this.getKunStorageByIndex(index);
        //存储单元中存储玩家的鲲池
        storage.put(mid, pool);
    }

}
