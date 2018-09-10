package com.qs.game.model.game;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Kun implements Serializable {
    private Long id;

    private String name;

    private Long score;

    private Integer type;

    private String ext1;

    private String ext2;

    private String ext3;

}