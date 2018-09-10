package com.qs.game.model.game;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class KunGoldLog implements Serializable {
    private Integer id;

    private Integer mid;

    private Byte type;

    private Long gold;

    private Long nowgold;

    private Byte action;

    private Integer time;

    private Date date;

    private Date dateTime;

    private String ext1;

    private String ext2;

    private String ext3;

}