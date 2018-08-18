package com.qs.game.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * Created by zun.wei on 2018/8/19.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface ShopChannel {

    /**
     * 发消息的通道名称
     */
    String SHOP_OUTPUT = "shop_output";

    /**
     * 消息的订阅通道名称
     */
    String SHOP_INPUT = "shop_input";

    /**
     * 发消息的通道
     *
     * @return
     */
    @Output(SHOP_OUTPUT)
    MessageChannel sendShopMessage();

    /**
     * 收消息的通道
     *
     * @return
     */
    @Input(SHOP_INPUT)
    SubscribableChannel recieveShopMessage();

}
