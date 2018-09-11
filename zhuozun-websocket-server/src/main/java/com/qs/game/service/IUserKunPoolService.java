package com.qs.game.service;

import com.qs.game.base.baseservice.IBaseService;
import com.qs.game.model.game.Kuns;
import com.qs.game.model.game.PoolCell;
import com.qs.game.model.game.UserKunPool;

import java.util.List;

/**
 * 玩家鲲池业务接口
 */
public interface IUserKunPoolService extends IBaseService<UserKunPool, Long> {

    /**
     *  根据mid 查询鲲池列表
     * @param mid 玩家mid
     * @return 鲲池列表
     */
    List<UserKunPool> queryListByMid(Integer mid);

    /**
     * 批量插入
     * @param mid 玩家mid
     * @param poolCellList 鲲池单元列表
     * @return
     */
    int insertBatch(String mid, List<PoolCell> poolCellList);

}
