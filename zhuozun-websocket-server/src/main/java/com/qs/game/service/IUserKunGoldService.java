package com.qs.game.service;

import com.qs.game.base.baseservice.IBaseService;
import com.qs.game.model.game.UserKunGold;

/**
 * 用户养鲲金币表业务接口
 */
public interface IUserKunGoldService extends IBaseService<UserKunGold, Long> {

    /**
     *  根据玩家mid 查询玩家金币数
     * @param mid 玩家mid
     * @return 玩家金币数
     */
    UserKunGold selectByMid(Integer mid);

}
