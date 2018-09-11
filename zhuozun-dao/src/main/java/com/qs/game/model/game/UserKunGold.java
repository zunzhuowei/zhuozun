package com.qs.game.model.game;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserKunGold implements Serializable {
    private Long id;

    private Integer mid;

    private Long gold;

    private String ext1;

    private String ext2;

    private String ext3;

}