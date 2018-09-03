package com.qs.game.common;


import com.qs.game.cache.CacheKey;
import com.qs.game.model.base.ReqEntity;
import com.qs.game.service.IRedisService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Objects;

@Component
public class Global {


    @Resource
    private IRedisService redisService;

    public static final AttributeKey<String> attrToken = AttributeKey.valueOf("netty.channel.token"); //token
    public static final AttributeKey<String> attrSkey = AttributeKey.valueOf("netty.channel.sKey"); //signKey
    public static final AttributeKey<String> attrUid = AttributeKey.valueOf("netty.channel.uId"); //userId
    public static final AttributeKey<ReqEntity> REQUEST_ENTITY = AttributeKey.valueOf("netty.channel.reqEntity"); //reqEntity

    /**
     * 当前在线人员
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
//    private static ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);

    public static ChannelGroup getChannelGroup() {
        return channelGroup;
    }

    /**
     *  查找对应客户端的client channel
     * @param channelId ChannelId
     * @return 如果不存在为 null
     */
    public Channel findChannel(ChannelId channelId) {
        return channelGroup.find(channelId);
    }


    /**
     * 添加到在线用户组
     * @param uId 用户id
     * @param channel 用户频道
     * @return 添加成功或者失败
     */
    public boolean add2ChannelGroup(String uId, Channel channel) {
        assert channel != null;
        String key = this.getRedisKeyByUid(uId);
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


    /**
     *  从在线用户组剔除某个客户端
     * @param uId 用户id
     * @param channel 频道
     * @return 剔除成功或者失败
     */
    public boolean delChannelFromGroup(String uId, Channel channel) {
        assert channel != null;
        String key = this.getRedisKeyByUid(uId);
        try {
            redisService.del(key);
        } finally {
            channelGroup.remove(channel);
        }
        return true;
    }


    /**
     * 根据用户id 获取channelId
     * @param uId 用户id
     * @return ChannelId
     */
    public ChannelId getChannelIdByUid(String uId) {
        String key = this.getRedisKeyByUid(uId);
        Object chObj = redisService.getObj(key);
        return Objects.isNull(chObj) ? null : (ChannelId) chObj;
    }

    /**
     *  根据用户id获取客户端频道longId
     * @param uId 用户id
     * @return channelId.asLongText
     */
    public String getChannelLongIdByUid(String uId) {
        ChannelId channelId = this.getChannelIdByUid(uId);
        return Objects.isNull(channelId) ? null : channelId.asLongText();
    }


    /**
     * 发送消息给所有在线客户端
     * @param message TextWebSocketFrame
     * @return 发送成功与否
     */
    public boolean sendMsg2All(TextWebSocketFrame message) {
        ChannelGroupFuture channelGroupFuture = channelGroup.writeAndFlush(message);
        return channelGroupFuture.isSuccess();
    }

    /**
     * 发送消息给所有在线客户端
     * @param message String
     * @return 发送成功与否
     */
    public boolean sendMsg2All(String message) {
        ChannelGroupFuture channelGroupFuture = channelGroup.writeAndFlush(new TextWebSocketFrame(message));
        return channelGroupFuture.isSuccess();
    }

    /**
     * 发送消息给指定客户端
     * @param message TextWebSocketFrame
     * @param uId 在线用户id
     * @return 发送成功与否
     */
    public boolean sendMsg2One(TextWebSocketFrame message, String uId) {
        String channelLongId = this.getChannelLongIdByUid(uId);
        return StringUtils.isNotBlank(channelLongId)
                && this.sendMsgByMatcher(channelLongId, message);
    }

    /**
     * 发送消息给指定客户端
     * @param message String
     * @param uId 在线用户id
     * @return 发送成功与否
     */
    public boolean sendMsg2One(String message, String uId) {
        String channelLongId = this.getChannelLongIdByUid(uId);
        return StringUtils.isNotBlank(channelLongId)
                && this.sendMsgByMatcher(channelLongId, new TextWebSocketFrame(message));
    }

    /**
     *  发送消息给指定某些客户端
     * @param message TextWebSocketFrame
     * @param uIds 某些在线用户id
     */
    public void sendMsg2Some(TextWebSocketFrame message, String... uIds) {
        Arrays.asList(uIds).forEach(uId -> {
            String channelLongId = this.getChannelLongIdByUid(uId);
            if (StringUtils.isNotBlank(channelLongId))
                this.sendMsgByMatcher(channelLongId, message);
        });
    }

    /**
     *  发送消息给指定某些客户端
     * @param message String
     * @param uIds 某些在线用户id
     */
    public void sendMsg2Some(String message, String... uIds) {
        Arrays.asList(uIds).forEach(uId -> {
            String channelLongId = this.getChannelLongIdByUid(uId);
            this.sendMsgByMatcher(channelLongId, new TextWebSocketFrame(message));
        });
    }

    /**
     *  根据用户id 获取Redis 的缓存key
     * @param uId 用户id
     * @return 缓存key
     */
    private String getRedisKeyByUid(String uId) {
        return CacheKey.RedisPrefix.USER_ID_PREFIX.KEY + uId;
    }


    /**
     * @param channelLongId asLongText
     */
    public boolean sendMsgByMatcher(String channelLongId, TextWebSocketFrame message) {
        ChannelMatcher matcher = (channel) -> channel.id().asLongText().startsWith(channelLongId);
        ChannelGroupFuture channelGroupFuture = channelGroup.writeAndFlush(message, matcher,true)
                .addListener((ChannelFutureListener) channelFuture -> {
                    boolean su = channelFuture.isSuccess();
                    if (!su) {
                        reTrySendMsg(channelFuture, message, 5);
                    }
                });
        return channelGroupFuture.isSuccess();
    }

    /**
     * 重试发送
     * @param channelFuture 频道future
     * @param message 发送的消息
     * @param times 重试次数
     */
    private void reTrySendMsg(ChannelFuture channelFuture, TextWebSocketFrame message, int times) {
        boolean su = channelFuture.channel().writeAndFlush(message).isSuccess();
        if (!su) {
            if (times > 0)
                reTrySendMsg(channelFuture, message, --times);
        }
    }

}
