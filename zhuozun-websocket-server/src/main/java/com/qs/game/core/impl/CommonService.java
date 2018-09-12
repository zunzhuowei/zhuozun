package com.qs.game.core.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qs.game.cache.CacheKey;
import com.qs.game.common.game.KunGold;
import com.qs.game.config.game.GameManager;
import com.qs.game.core.ICommonService;
import com.qs.game.model.game.*;
import com.qs.game.service.IRedisService;
import com.qs.game.service.IUserKunGoldService;
import com.qs.game.service.IUserKunPoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by zun.wei on 2018/9/10 19:17.
 * Description: 公共业务接口
 */
@Slf4j
@Service
public class CommonService implements ICommonService {


    @Autowired
    private IRedisService redisService;

    @Autowired
    private IUserKunGoldService userKunGoldService;

    @Autowired
    private IUserKunPoolService userKunPoolService;

    @Autowired
    private GameManager gameManager;

    @Override
    public Pool updatePoolByKuns(String mid, int cellNo, Kuns updateKuns) {
        //查询玩家鲲池
        Pool pool = this.getPlayerKunPool(mid);
        return this.updateSrcPoolByKuns(mid, pool, cellNo, updateKuns);
    }

    @Override
    public Pool updatePoolByPoolCell(String mid, PoolCell poolCell) {
        return this.updatePoolByKuns(mid, poolCell.getNo(), poolCell.getKuns());
    }

    @Override
    public Pool updatePoolByOldPool(String mid, Pool srcPool, PoolCell poolCell) {
        return this.updateSrcPoolByKuns(mid, srcPool, poolCell.getNo(), poolCell.getKuns());
    }

    @Override
    public Pool updateSrcPoolByKuns(String mid, Pool srcPool, int cellNo, Kuns updateKuns) {
        return Optional.ofNullable(srcPool).map(e -> {
            List<PoolCell> poolCells = e.getPoolCells()
                    .stream()
                    .peek(poolCell -> {
                        if (poolCell.getNo() == cellNo)
                            poolCell.setKuns(updateKuns);
                    }).collect(toList());
            e.setPoolCells(poolCells);
            //把更新后的鲲池保存
            this.savePool2CacheAndMemory(mid, e);
            return e;
        }).orElseGet(null);
    }

    @Override
    public long getPlayerGold(String mid) {
        String goldKey = CacheKey.RedisPrefix.USER_KUN_GOLD.KEY + mid;
        String goldStr = redisService.get(goldKey);
        return Optional.ofNullable(goldStr)
                .map(Long::parseLong)
                .orElseGet(() -> {
                    UserKunGold userKunGold = userKunGoldService.selectByPrimaryKey(Long.valueOf(mid));
                    return Optional.ofNullable(userKunGold).map(ukg -> {
                        Long gold = ukg.getGold();
                        redisService.set(goldKey, gold);
                        return gold;
                    }).orElse(0L);
                });

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long addPlayerGold(String mid, long addGold) {
        String goldKey = CacheKey.RedisPrefix.USER_KUN_GOLD.KEY + mid;
        Long newGold = redisService.incr(goldKey, addGold);
        // 重复索引则更新
        userKunGoldService.insertSelective
                (new UserKunGold().setGold(newGold).setMid(Integer.parseInt(mid)));
        /*UserKunGold userKunGold = userKunGoldService.selectByMid(Integer.valueOf(mid));
        Optional.ofNullable(userKunGold)
                .map(e -> userKunGoldService.updateByPrimaryKeySelective(e.setGold(newGold)))
                .orElseGet(() -> userKunGoldService.insertSelective
                        (new UserKunGold().setGold(newGold).setMid(Integer.parseInt(mid))));*/
        return newGold;
    }

    @Override
    public Pool getPlayerKunPool(String mid) {
        //查看内存中是否已经保存了该玩家的鲲池数据下标
        Integer index = gameManager.getUserKunPoolPosition().get(Integer.valueOf(mid));
        return Optional.ofNullable(index)
                //从内存中取出玩家鲲池信息
                .map(e -> {
                    String json = gameManager.getMemoryKunPool(mid, e);
                    return JSONObject.parseObject(json, Pool.class);
                })
                .orElseGet(() -> {
                    String kunKey = CacheKey.RedisPrefix.USER_KUN_POOL.KEY + mid;
                    //缓存中的鲲池
                    String poolJson = redisService.get(kunKey);
                    return Optional.ofNullable(poolJson)
                            .map(e -> JSONObject.parseObject(poolJson, Pool.class))
                            .orElseGet(() -> {
                                //如果缓存中没有数据查询数据库
                                List<PoolCell> poolCells = this.getPoolCellsFromDB(mid);
                                return new Pool().setPoolCells(poolCells);
                            });
                });
    }

    @Override
    public List<PoolCell> getPoolCellsFromDB(String mid) {
        List<UserKunPool> UserKunPools = userKunPoolService.queryListByMid(Integer.valueOf(mid));
        return Optional.ofNullable(UserKunPools)
                .map(j -> {
                    if (j.isEmpty()) {
                        return this.getInitPool(mid).getPoolCells();
                    } else {
                        return j.stream().map(m ->
                                new PoolCell().setNo(m.getPosition())
                                        .setKuns(new Kuns().setTime(m.getRunTime())
                                                .setWork(m.getIsRun()).setType(m.getType())))
                                .collect(toList());
                    }
                }).orElseGet(() -> {
                    Pool initKunPool = this.getInitPool(mid);
                    return initKunPool.getPoolCells();
                });
    }

    //获取初始化鲲池
    @Override
    public Pool getInitPool(String mid) {
        String kunKey = CacheKey.RedisPrefix.USER_KUN_POOL.KEY + mid;
        //如果数据库也不存在则，初始化鲲池
        Pool initKunPool = gameManager.getInitKunPool();
        String initPoolJson = JSONObject.toJSONString(initKunPool, SerializerFeature.DisableCircularReferenceDetect);
        //保存到缓存中
        redisService.set(kunKey, initPoolJson);
        //保存到内存中
        gameManager.storageOnMemory(mid, initPoolJson);
        return initKunPool;
    }

    @Override
    public boolean savePool2CacheAndMemory(String mid, Pool pool) {
        String kunKey = CacheKey.RedisPrefix.USER_KUN_POOL.KEY + mid;
        String poolJson = JSONObject.toJSONString(pool, SerializerFeature.DisableCircularReferenceDetect);
        //保存到缓存中
        boolean b = redisService.set(kunKey, poolJson);
        //保存到内存中
        gameManager.storageOnMemory(mid, poolJson);
        return b;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean persistenceUserKunInfos(String mid) {
        //获取当前的玩家当前的池
        Pool pool = this.getPlayerKunPool(mid);
        List<PoolCell> poolCells = pool.getPoolCells();
        long nowTime = new Date().getTime() / 1000;
        //截止目前为止生产出的金币
        long productGold = Optional.ofNullable(poolCells)
                .map(pcs -> pcs.stream().map(PoolCell::getKuns)
                        //筛选类型存在并且在工作的
                        .filter(e -> e.getType() > 0 && e.getWork() > 0)
                        //根据间隔时间和类型计算出没种鲲产的金币数
                        .map(e -> (nowTime - e.getTime()) * KunGold.goldByType(e.getType()))
                        //累加所有类型
                        .reduce((e1, e2) -> e1 + e2).orElse(0L)
                ).orElse(0L);
        //添加金币,并持久化（redis 、 db）
        long nowGold = this.addPlayerGold(mid, productGold);

        //更改每只鲲的工作时间
        poolCells = Optional.ofNullable(poolCells)
                //重置工作时间为当前时间
                .map(e -> e.stream().peek(i -> {
                    //只更新存在的类型和正在工作的鲲
                    if (i.getKuns().getType() > 0 && i.getKuns().getWork() > 0)
                        i.setKuns(i.getKuns().setTime(nowTime));
                }).collect(toList()))
                .orElseGet(ArrayList::new);

        //持久化到缓存中
        this.savePool2CacheAndMemory(mid, new Pool().setPoolCells(poolCells));

        //持久化到db
        userKunPoolService.insertBatch(mid, poolCells);

        return true;
    }

    @Override
    public long updateGoldByNo(String mid, Pool pool, Integer noIndex) {
        List<PoolCell> poolCells = pool.getPoolCells();
        //截止目前为止生产出的金币
        return updateGoldByNo(mid, poolCells, noIndex);
    }

    @Override
    public long updateGoldByNo(String mid, Integer noIndex) {
        //获取玩家的鲲池
        Pool pool = this.getPlayerKunPool(mid);
        return updateGoldByNo(mid, pool, noIndex);
    }

    @Override
    public long updateGoldByNo(String mid, List<PoolCell> poolCells, Integer noIndex) {
        long nowTime = new Date().getTime() / 1000;
        //截止目前为止生产出的金币
        long productGold = Optional.ofNullable(poolCells)
                .map(pcs -> pcs.stream()
                        .filter(e -> Objects.equals(e.getNo(), noIndex))
                        .map(PoolCell::getKuns)
                        //筛选类型存在并且在工作的
                        .filter(e -> e.getType() > 0 && e.getWork() > 0)
                        //根据间隔时间和类型计算出没种鲲产的金币数
                        .map(e -> (nowTime - e.getTime()) * KunGold.goldByType(e.getType()))
                        //累加所有类型
                        .reduce((e1, e2) -> e1 + e2).orElse(0L)
                ).orElse(0L);
        //添加金币,并持久化（redis 、 db）
        return this.addPlayerGold(mid, productGold);
    }


   /* public static void main(String[] args) throws ParseException {
        FastDateFormat fastDateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
        long nowTime = fastDateFormat.parse("2018-09-10 23:57:01").getTime() / 1000;
        long beginTime = fastDateFormat.parse("2018-09-10 23:57:00").getTime() / 1000;
        System.out.println("nowTime = " + (nowTime - beginTime));

    }*/

}
