package com.qs.game.common;


import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Global {

    /**
     * 当前在线人员
     */
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    /**
     *  查找对应客户端的client channel
     * @param channelId ChannelId
     * @return 如果不存在为 null
     */
    public Channel findChannel(ChannelId channelId) {
        return channelGroup.find(channelId);
    }


}
