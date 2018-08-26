package com.qs.game.common;


import com.qs.game.cache.CacheKey;
import com.qs.game.service.IRedisService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Component
public class Global {


    @Resource
    private IRedisService redisService;


    /**
     * 当前在线人员
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static ChannelGroup getChannelGroup() {
        return channelGroup;
    }

    /**
     *  查找对应客户端的client channel
     * @param channelId ChannelId
     * @return 如果不存在为 null
     */
    public Channel findChannel(ChannelId channelId) {
        return channelGroup.find(channelId);//channelGroup.write();
    }


    public boolean add2ChannelGroup(Channel channel) {
        assert channel != null;
        String key = this.getRedisKeyByChannel(channel);
        boolean redis = redisService.set(key, channel.id());
        boolean memory = channelGroup.add(channel);
        if (redis && memory) {
            return true;
        } else {
            try {
                redisService.del(key);
            } finally {
                channelGroup.remove(channel);
            }
            return false;
        }
    }


    public boolean delChannelFromGroup(Channel channel) {
        assert channel != null;
        String key = this.getRedisKeyByChannel(channel);
        try {
            redisService.del(key);
        } finally {
            channelGroup.remove(channel);
        }
        return true;
    }


    /**
     * @param channelIdStr asLongText
     */
    public Channel getChannelById(String channelIdStr) {
        channelIdStr = Objects.isNull(channelIdStr) ? "" : channelIdStr;
        Object obj = redisService.getObj(channelIdStr);
        if (Objects.isNull(obj)) return null;
        String key = this.getRedisKeyByChannel(channelIdStr);
        ChannelId channelId = (ChannelId) redisService.getObj(key);
        if (Objects.isNull(channelId)) return null;
        return channelGroup.find(channelId);
    }

    /**
     * @param toChannelIdStr asLongText
     */
    public boolean sendMsg2One(Object message, String toChannelIdStr) {
        Channel target = this.getChannelById(toChannelIdStr);
        if (Objects.isNull(target)) return false;
        ChannelFuture channelFuture = target.writeAndFlush(message);
        return channelFuture.isSuccess();
    }


    public boolean sendMsg2All(Object message) {
        ChannelGroupFuture channelGroupFuture = channelGroup.writeAndFlush(message);
        return channelGroupFuture.isSuccess();
    }

    /**
     * @param matcherStr asLongText
     */
    public boolean sendMsgByMatcher(String matcherStr, Object message) {
        ChannelMatcher matcher = (channel) -> channel.id().asLongText().startsWith(matcherStr);
        ChannelGroupFuture channelGroupFuture = channelGroup.writeAndFlush(message, matcher,true);
        return channelGroupFuture.isSuccess();
    }


    private String getRedisKeyByChannel(Channel channel) {
        return CacheKey.RedisPrefix.WEBSOCKET_USER_PREFIX.KEY + channel.id().asLongText();
    }

    private String getRedisKeyByChannel(String channelIdStr) {
        return CacheKey.RedisPrefix.WEBSOCKET_USER_PREFIX.KEY + channelIdStr;
    }


}
