package com.qs.game.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Created by zun.wei on 2018/8/18.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *
 * kafka source definition
 *
 */
public interface Source {

    //发送队列1
    String OUTPUT = "output";

    @Output(Source.OUTPUT)
    MessageChannel output();

}
