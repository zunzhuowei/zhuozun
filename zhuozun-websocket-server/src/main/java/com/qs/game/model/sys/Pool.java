package com.qs.game.model.sys;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 鲲池
 */
@Data
@Accessors(chain = true)
public class Pool implements Serializable {

    //鲲池由16个单元格组成
    private List<PoolCell> poolCells = new LinkedList<>();

}
