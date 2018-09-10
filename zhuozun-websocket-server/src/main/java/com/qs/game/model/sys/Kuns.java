package com.qs.game.model.sys;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by zun.wei on 2018/9/7 19:49.
 * Description: 鲲对象
 */
@Data
@Accessors(chain = true)
public class Kuns implements Serializable {

    private long gold; //每秒产生的金币数

    private long time; //呆在海里的开始时间戳

    private int work; // 鲲所在位置，0家里或者1海里

    private int type; //鲲的类型,0不存在，1+ 表示其他类型

    //private int isEmpty; // 位置是否为空 0 空，1 不为空


}
