package com.qs.game.model.game;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserKunBuyLog implements Serializable {
    private Long id;

    private Integer mid;

    private Integer type;

    private Long num;

    private String ext1;

    private String ext2;

    private String ext3;

}