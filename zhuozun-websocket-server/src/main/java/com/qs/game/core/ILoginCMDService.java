package com.qs.game.core;

import com.qs.game.model.game.PoolCell;

import java.util.List;

/**
 * Created by zun.wei on 2018/9/6 13:49.
 * Description: 登录命令业务接口
 */
public interface ILoginCMDService extends ICMDService{

    /**
     *  获取用户的产分速度
     * @param mid 玩家mid
     * @return 每秒产分速度
     */
    long getPlayerGoldSpeedByMid(String mid);

    /**
     *  获取用户的产分速度
     * @param kunPoolJson 玩家鲲池json字符串
     * @return 每秒产分速度
     */
    long getPlayerGoldSpeedByKunPoolJson(String kunPoolJson);

    /**
     *  获取玩家鲲池数据
     * @param mid 玩家mid
     * @return 鲲池数据json数据
     */
    List<PoolCell> getPlayerKunPoolCells(String mid);

}

