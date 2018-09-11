package com.qs.game.core;

import com.qs.game.model.game.Kuns;
import com.qs.game.model.game.Pool;
import com.qs.game.model.game.PoolCell;

import java.util.List;

/**
 * Created by zun.wei on 2018/9/10 19:16.
 * Description: 公共业务接口
 */
public interface ICommonService {


    /**
     * 根据给定当鲲池编号和要更新的鲲和玩家id更新鲲池
     *
     * @param mid        玩家mid
     * @param cellNo     鲲池编号
     * @param updateKuns 要更新成为的鲲池
     * @return 更新后的鲲池
     */
    Pool updatePoolByKuns(String mid, int cellNo, Kuns updateKuns);

    /**
     * 根据玩家id 和鲲池单元格更新鲲池
     *
     * @param mid      玩家mid
     * @param poolCell 鲲池单元格
     * @return 更新后的鲲池
     */
    Pool updatePoolByPoolCell(String mid, PoolCell poolCell);

    /**
     *  根据给定的鲲池以及鲲池单元格更新鲲池
     * @param mid 玩家mid
     * @param srcPool 源鲲池
     * @param poolCell 要更新的鲲池单元格
     * @return 更新后的鲲池
     */
    Pool updatePoolByOldPool(String mid, Pool srcPool, PoolCell poolCell);

    /**
     * 根据给定鲲池和单元格编号以及要更新的鲲对象更新玩家鲲池
     *
     * @param mid        玩家mid
     * @param srcPool    源鲲池
     * @param cellNo     鲲池编号
     * @param updateKuns 要更新的鲲对象
     * @return 更新后的鲲池
     */
    Pool updateSrcPoolByKuns(String mid, Pool srcPool, int cellNo, Kuns updateKuns);

    /**
     *  获取玩家的金币数
     * @param mid 玩家mid
     * @return 金币数
     */
    long getPlayerGold(String mid);

    /**
     * 添加玩家金币,添加缓存及数据库
     *
     * @param mid     玩家id
     * @param addGold 添加的金币数
     * @return 玩家最新的金币
     */
    Long addPlayerGold(String mid, long addGold);

    /**
     * 获取玩家鲲池数据
     *
     * @param mid 玩家mid
     * @return 鲲池 数据
     */
    Pool getPlayerKunPool(String mid);

    /**
     * 保存鲲池
     *
     * @param mid  玩家mid
     * @param pool 鲲池
     * @return success or fail
     */
    boolean savePool2CacheAndMemory(String mid, Pool pool);

    /**
     * 持久化玩家鲲相关信息
     *
     * @param mid 玩家mid
     * @return 成功与否
     */
    boolean persistenceUserKunInfos(String mid);

    /**
     * 根据鲲池单元格编号位置更新到至上次投放之后到目前为止该鲲赚到的金币
     * @param mid 玩家Mid
     * @param pool 鲲池
     * @param noIndex 位置下标
     * @return 历史总金币数
     */
    long updateGoldByNo(String mid, Pool pool, Integer noIndex);

    /**
     *  根据鲲池单元格编号位置更新到至上次投放之后到目前为止该鲲赚到的金币
     * @param mid 玩家Mid
     * @param noIndex 位置下标
     * @return 历史总金币数
     */
    long updateGoldByNo(String mid, Integer noIndex);

    /**
     * 根据鲲池单元格编号位置更新到至上次投放之后到目前为止该鲲赚到的金币
     * @param mid 玩家Mid
     * @param poolCells 鲲池单元格列表
     * @param noIndex 位置下标
     * @return 历史总金币数
     */
    long updateGoldByNo(String mid, List<PoolCell> poolCells, Integer noIndex);
}
