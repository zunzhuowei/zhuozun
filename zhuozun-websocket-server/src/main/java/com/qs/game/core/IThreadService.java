package com.qs.game.core;

import com.qs.game.model.base.ReqEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 业务线程接口
 */
public interface IThreadService {

    ExecutorService executor =
            new ThreadPoolExecutor(12, 16, 0L,
                    TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(100000));//CPU核数4-10倍

    /**
     *  启动线程
     * @param runnable Runnable
     */
    void run(Runnable runnable);

    /**
     *  tcp握手
     * @param ctx ChannelHandlerContext
     * @param evt Object
     */
    void handshake(ChannelHandlerContext ctx, Object evt);

    /**
     * 处理客户端的心跳
     * @param ctx ChannelHandlerContext
     * @param msg Object
     */
    void heartbeat(ChannelHandlerContext ctx, Object msg);

    /**
     *  access channel read
     * @param ctx ChannelHandlerContext
     * @param msg TextWebSocketFrame
     */
    void accessChannelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg);

    /**
     * 业务处理
     * @param ctx ChannelHandlerContext
     * @param msg TextWebSocketFrame
     * @param reqEntity ReqEntity
     */
    void doBusiness(ChannelHandlerContext ctx, TextWebSocketFrame msg, ReqEntity reqEntity);

    /**
     *  当玩家掉线离开时处理业务
     * @param uid 玩家mid
     */
    void handlerRemoved(String uid);
}
