package com.qs.game.model.game;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserKunPool implements Serializable {
    private Long id;

    private Integer mid;

    private Integer type;

    private Integer position;

    private Integer isRun;

    private Integer runTime;

    private String ext1;

    private String ext2;

    private String ext3;

}