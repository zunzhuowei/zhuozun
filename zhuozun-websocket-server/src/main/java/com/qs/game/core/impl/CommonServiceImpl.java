package com.qs.game.core.impl;

import com.alibaba.fastjson.JSONObject;
import com.qs.game.cache.CacheKey;
import com.qs.game.config.game.GameManager;
import com.qs.game.model.game.UserKunGold;
import com.qs.game.model.game.Kuns;
import com.qs.game.model.game.Pool;
import com.qs.game.model.game.PoolCell;
import com.qs.game.core.ICommonService;
import com.qs.game.service.IRedisService;
import com.qs.game.service.IUserKunGoldService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * Created by zun.wei on 2018/9/10 19:17.
 * Description: 公共业务接口
 */
@Service
public class CommonServiceImpl implements ICommonService {


    @Autowired
    private IRedisService redisService;

    @Resource
    private IUserKunGoldService userKunGoldService;

    @Autowired
    private GameManager gameManager;

    @Override
    public Pool updatePoolByKuns(String mid, int cellNo, Kuns updateKuns) {
        //查询玩家鲲池
        Pool pool = this.getPlayerKunPool(mid);
        if (Objects.isNull(pool)) return null;
        List<PoolCell> poolCells = pool.getPoolCells();
        PoolCell pc = poolCells.remove(cellNo);
        //更新鲲池
        poolCells.add(pc.setKuns(updateKuns));
        pool.setPoolCells(poolCells);
        //把更新后的鲲池保存
        this.savePool(mid, pool);
        return pool;
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
        if (Objects.isNull(srcPool)) return null;
        List<PoolCell> poolCells = srcPool.getPoolCells();
        PoolCell pc = poolCells.remove(cellNo);
        //更新鲲池
        poolCells.add(pc.setKuns(updateKuns));
        srcPool.setPoolCells(poolCells);
        //把更新后的鲲池保存
        this.savePool(mid, srcPool);
        return srcPool;
    }

    @Override
    public long getPlayerGold(String mid) {
        String goldKey = CacheKey.RedisPrefix.USER_KUN_GOLD.KEY + mid;
        String goldStr = redisService.get(goldKey);
        if (Objects.isNull(goldStr)) {
            UserKunGold userKunGold = userKunGoldService.selectByPrimaryKey(Long.valueOf(mid));
            if (Objects.nonNull(userKunGold)) {
                Long gold = userKunGold.getGold();
                redisService.set(goldKey, gold);
                return gold;
            } else {
                return 0L;
            }
        } else {
            return Long.parseLong(goldStr);
        }
    }

    @Override
    public Long addPlayerGold(String mid, long addGold) {
        String goldKey = CacheKey.RedisPrefix.USER_KUN_GOLD.KEY + mid;
        Long newGold = redisService.incr(goldKey, addGold);
        UserKunGold userKunGold = userKunGoldService.selectByPrimaryKey(Long.valueOf(mid));
        if (Objects.nonNull(userKunGold)) {
            userKunGoldService.updateByPrimaryKeySelective(userKunGold.setGold(newGold));
        } else {
            userKunGoldService.insertSelective(new UserKunGold().setGold(newGold).setMid(Integer.parseInt(mid)));
        }
        return newGold;
    }

    @Override
    public Pool getPlayerKunPool(String mid) {
        //查看内存中是否已经保存了该玩家的鲲池数据下标
        Integer index = gameManager.getUserKunPoolPosition().get(Integer.valueOf(mid));
        if (Objects.nonNull(index)) {
            //从内存中取出玩家鲲池信息
            return gameManager.getMemoryKunPool(mid, index);
        } else {
            String kunKey = CacheKey.RedisPrefix.USER_KUN_POOL.KEY + mid;
            //缓存中的鲲池
            String poolJson = redisService.get(kunKey);
            if (StringUtils.isBlank(poolJson)) {
                //初始化鲲池
                Pool pool = gameManager.getInitKunPool();
                poolJson = JSONObject.toJSONString(pool);
                //保存到缓存中
                redisService.set(kunKey, poolJson);
                //保存到内存中
                gameManager.storageOnMemory(mid, pool);
                return pool;
            } else {
                //解析缓存中的鲲池数据
                Pool pool = JSONObject.parseObject(poolJson, Pool.class);
                //保存到内存中
                gameManager.storageOnMemory(mid, pool);
                return pool;
            }
        }
    }

    @Override
    public boolean savePool(String mid, Pool pool) {
        String kunKey = CacheKey.RedisPrefix.USER_KUN_POOL.KEY + mid;
        String poolJson = JSONObject.toJSONString(pool);
        //保存到缓存中
        boolean b = redisService.set(kunKey, poolJson);
        //保存到内存中
        gameManager.storageOnMemory(mid, pool);
        return b;
    }


    //TODO 添加记录玩家离线时候根据内存鲲池数据持久化数据。


}
