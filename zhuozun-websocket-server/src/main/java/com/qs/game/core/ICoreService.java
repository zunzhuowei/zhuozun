package com.qs.game.core;

import com.qs.game.common.game.CommandService;
import com.qs.game.model.base.ReqEntity;
import com.qs.game.utils.SpringBeanUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 核心业务层接口
 */
public interface ICoreService {

    //命令路由业务集合
    Map<Integer, ICMDService> COMMAND_CMD_SERVICE = new ConcurrentHashMap<>();

    default void initRouter() {
        if (COMMAND_CMD_SERVICE.isEmpty()) {
            SpringBeanUtil.getCMDServiceBeans().entrySet().parallelStream()
                    .filter(e -> e.getValue().getClass().isAnnotationPresent(CommandService.class))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toSet())
                    .forEach(e -> COMMAND_CMD_SERVICE.put(e.getClass().getAnnotation(CommandService.class).value().VALUE, e));
        }
    }

    /**
     *  根据请求命令 路由分发请求
     * @param ctx ChannelHandlerContext
     * @param msg 请求消息
     * @param reqEntity 请求实体类
     */
    Runnable CmdRouter(ChannelHandlerContext ctx, TextWebSocketFrame msg, ReqEntity reqEntity);

    /**
     *  校验token有效性
     * @param ctx ChannelHandlerContext
     * @param request FullHttpRequest
     */
    //Runnable verifyToken(ChannelHandlerContext ctx, FullHttpRequest request);

    /**
     *  tcp握手
     * @param ctx ChannelHandlerContext
     * @param evt 请求事件
     */
    Runnable handshake(ChannelHandlerContext ctx, Object evt);

    /**
     *  access channel read
     * @param ctx ChannelHandlerContext
     * @param msg 请求消息
     */
    Runnable accessChannelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg);

    /**
     *  客户端的心跳
     * @param ctx ChannelHandlerContext
     * @param msg 请求消息
     */
    Runnable heartbeat(ChannelHandlerContext ctx, Object msg);

    /**
     * 当玩家掉线离开时处理业务
     * @param mid 玩家mid
     */
    Runnable handlerRemoved(String mid);

}
