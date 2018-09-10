package com.qs.game.model.sys;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 鲲池单元格
 */
@Data
@Accessors(chain = true)
public class PoolCell implements Serializable {

    private Integer no; //单元格编号

    private Kuns kuns; //鲲



}
