package com.qs.game.channel;

import com.qs.game.sink.Sink;
import com.qs.game.source.Source;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * Created by zun.wei on 2018/8/19.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface Channel extends Sink, Source {

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


    /**
     * 用户发消息的通道
     *
     * @return
     */
    @Output(USER_OUTPUT)
    MessageChannel sendUserMessage();

    /**
     * 用户收消息的通道
     *
     * @return
     */
    @Input(USER_INPUT)
    SubscribableChannel recieveUserMessage();

}
