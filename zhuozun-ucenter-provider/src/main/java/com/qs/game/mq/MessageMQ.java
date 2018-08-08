package com.qs.game.mq;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by zun.wei on 2018/8/8.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Data
@Accessors(chain = true)
public class MessageMQ implements Serializable {

    private Long msgId;

    private String content;

    private boolean success;

}
