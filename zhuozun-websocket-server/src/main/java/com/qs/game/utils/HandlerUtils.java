package com.qs.game.utils;


import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HandlerUtils {


    /**
     * 获取客户端短id
     */
    public static String getClientShortIdByChannel(Channel channel) {
        return channel.id().asShortText();
    }


    /**
     *  获取客户端长id
     */
    public static String getClientLongIdByChannel(Channel channel) {
        return channel.id().asLongText();
    }



}
