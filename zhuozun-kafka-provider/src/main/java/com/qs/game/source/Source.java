package com.qs.game.source;

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

    /**
     * 发消息的通道名称
     */
    String SHOP_OUTPUT = "shop_output";

    String USER_OUTPUT = "user_output";

}
