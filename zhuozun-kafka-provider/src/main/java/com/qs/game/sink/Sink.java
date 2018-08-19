package com.qs.game.sink;

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

    /**
     * 消息的订阅通道名称
     */
    String SHOP_INPUT = "shop_input";


    String USER_INPUT = "user_input";

}
