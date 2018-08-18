package com.qs.game.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Created by zun.wei on 2018/8/18.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *
 * kafka sink definition
 *
 */
public interface Sink {


    //接收队列1

    String INPUT = "input";

    @Input(Sink.INPUT)
    SubscribableChannel input();

}
