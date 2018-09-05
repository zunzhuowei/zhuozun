package com.qs.game.common;


import com.qs.game.model.base.ReqEntity;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Global {


    //@Resource
    //private IRedisService redisService;

    public static final AttributeKey<String> attrToken = AttributeKey.valueOf("netty.channel.token"); //token
    public static final AttributeKey<String> attrSkey = AttributeKey.valueOf("netty.channel.sKey"); //signKey
    public static final AttributeKey<String> attrUid = AttributeKey.valueOf("netty.channel.uId"); //userId
    public static final AttributeKey<ReqEntity> REQUEST_ENTITY = AttributeKey.valueOf("netty.channel.reqEntity"); //reqEntity

    /**
     * 当前在线人员
     */
    //private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static final Map<String, ChannelHandlerContext> sessionRepo = new ConcurrentHashMap<>();
    private static final String USER_ID = "userId:";

    public static Map<String, ChannelHandlerContext> getSessionRepo() {
        return sessionRepo;
    }

    /**
     * 添加到在线用户组
     * @param uId 用户id
     * @param ctx 用户频道上下文
     * @return 添加成功或者失败
     */
    public boolean add2SessionRepo(String uId, ChannelHandlerContext ctx) {
        ChannelHandlerContext context = this.getChannelHandlerContext(uId);
        if (Objects.nonNull(context)) {
            this.delCtxFromSessionRepo(uId); //先删除后插入
            context.channel().close();
        }

        String key = this.getOnlineUserKeyByUid(uId);
        ChannelHandlerContext result = sessionRepo.put(key, ctx);
        return Objects.nonNull(result);
    }


    /**
     *  从在线用户组剔除某个客户端
     * @param uId 用户id
     * @return 剔除成功或者失败
     */
    public void delCtxFromSessionRepo(String uId) {
        String key = this.getOnlineUserKeyByUid(uId);
        sessionRepo.remove(key);
    }


    /**
     * 根据用户id 获取channelId
     * @param uId 用户id
     * @return ChannelId
     */
    public ChannelId getChannelIdByUid(String uId) {
        String key = this.getOnlineUserKeyByUid(uId);
        ChannelHandlerContext context = sessionRepo.get(key);
        return Objects.isNull(context) ? null : context.channel().id();
    }

    /**
     *  获取 ChannelHandlerContext
     * @param uId 用户mid
     * @return ChannelHandlerContext
     */
    public ChannelHandlerContext getChannelHandlerContext(String uId) {
        String key = this.getOnlineUserKeyByUid(uId);
        ChannelHandlerContext context = sessionRepo.get(key);
        return Objects.isNull(context) ? null : context;
    }

    /**
     * 关闭 channel 和 删除session
     * @param uId 用户id
     */
    public void closeAndDelSession(String uId) {
        ChannelHandlerContext context = this.getChannelHandlerContext(uId);
        if (Objects.nonNull(context)) {
            context.channel().close();
        }
        this.delCtxFromSessionRepo(uId);
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
        sessionRepo.forEach((key, value) -> {
            Channel channel = value.channel();
            if (Objects.nonNull(channel) && channel.isActive()) {
                channel.writeAndFlush(message);
            }
        });
        return true;
    }

    /**
     * 发送消息给所有在线客户端
     * @param message String
     * @return 发送成功与否
     */
    public boolean sendMsg2All(String message) {
        return this.sendMsg2All(new TextWebSocketFrame(message));
    }

    /**
     * 发送消息给指定客户端
     * @param message TextWebSocketFrame
     * @param uId 在线用户id
     * @return 发送成功与否
     */
    public boolean sendMsg2One(TextWebSocketFrame message, String uId) {
        String key = this.getOnlineUserKeyByUid(uId);
        ChannelHandlerContext context = sessionRepo.get(key);
        ChannelFuture future = null;

        if (Objects.nonNull(context)) {
            Channel channel = context.channel();
            if (Objects.nonNull(channel) && channel.isActive()) {
                future = channel.writeAndFlush(message).addListener(
                        (ChannelFutureListener) channelFuture -> {
                        boolean su = channelFuture.isSuccess();
                        if (!su) {
                            reTrySendMsg(channelFuture, message, 5);
                        }
                });
            }
        }
        return Objects.nonNull(future) && future.isSuccess();
    }

    /**
     * 发送消息给指定客户端
     * @param message String
     * @param uId 在线用户id
     * @return 发送成功与否
     */
    public boolean sendMsg2One(String message, String uId) {
        return this.sendMsg2One(new TextWebSocketFrame(message), uId);
    }

    /**
     *  发送消息给指定某些客户端
     * @param message TextWebSocketFrame
     * @param uIds 某些在线用户id
     */
    public void sendMsg2Some(TextWebSocketFrame message, String... uIds) {
        Arrays.asList(uIds).forEach(uId -> this.sendMsg2One(message, uId));
    }

    /**
     *  发送消息给指定某些客户端
     * @param message String
     * @param uIds 某些在线用户id
     */
    public void sendMsg2Some(String message, String... uIds) {
        Arrays.asList(uIds).forEach(uId -> this.sendMsg2One(message, uId));
    }

    /**
     *  根据用户id 获取Redis 的缓存key
     * @param uId 用户id
     * @return 缓存key
     */
    public String getOnlineUserKeyByUid(String uId) {
        return USER_ID + uId;
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
