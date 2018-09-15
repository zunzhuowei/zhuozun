package com.qs.game.core;


import com.qs.game.model.game.PoolCell;

import java.util.List;

/**
 * 移动命令接口
 */
public interface IMoveCMDService extends ICMDService {



    /**
     *  更换位置
     * @param fromIndex 来自哪个单元格的下标
     * @param toIndex 移动到的单元格的下标
     * @param fromCell 来自哪个单元格
     * @param toCell 移动到哪个单元格
     * @param poolCells 玩家鲲池单元格列表
     * @return 移动后的鲲池单元格列表
     */
    List<PoolCell> switchNo(Integer fromIndex, Integer toIndex,
                            PoolCell fromCell, PoolCell toCell, List<PoolCell> poolCells);
}
