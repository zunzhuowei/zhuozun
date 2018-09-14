package com.qs.game.model.game;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
    玩家金币DTO对象
 */
@Data
@Accessors(chain = true)
public class GoldDto implements Serializable {

    private long newGold;

    private long addGold;

}
