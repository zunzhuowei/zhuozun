package com.qs.game.service;

import com.qs.game.model.base.ReqEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 核心业务层接口
 */
public interface ICoreService {

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

}
