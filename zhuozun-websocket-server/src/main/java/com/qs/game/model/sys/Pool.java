package com.qs.game.model.sys;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 鲲池
 */
@Data
@Accessors(chain = true)
public class Pool implements Serializable {

    //鲲池由16个单元格组成
    List<PoolCell> poolCells;

}
