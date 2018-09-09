package com.qs.game.service;

import com.qs.game.model.sys.Kuns;
import com.qs.game.model.sys.Pool;

import java.util.Map;

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
     *  获取玩家的金币数
     * @param mid 玩家mid
     * @return 金币数
     */
    long getPlayerGold(String mid);

    /**
     *  获取玩家鲲池数据
     * @param mid 玩家mid
     * @return 鲲池数据json数据
     */
    String getPlayerKunPoolCells(String mid);

    /**
     * 获取玩家鲲池数据
     * @param mid 玩家mid
     * @return 鲲池 数据
     */
    Pool getPlayerKunPool(String mid);

}

