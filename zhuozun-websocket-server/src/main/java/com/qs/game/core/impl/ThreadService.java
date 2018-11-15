package com.qs.game.core.impl;

import com.qs.game.core.ICoreService;
import com.qs.game.core.IThreadService;
import com.qs.game.model.base.ReqEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 业务线程工具类
 */
@Slf4j
@Service
public class ThreadService implements IThreadService {

    @Autowired
    private ICoreService coreService;


    //启动线程
    @Override
    public void run(Runnable runnable) {
        if (Objects.nonNull(runnable)) executor.execute(runnable);
    }

    // tcp握手
    @Override
    public void handshake(ChannelHandlerContext ctx, Object evt) {
        this.run(coreService.handshake(ctx, evt));
    }

    // 处理客户端的心跳
    @Override
    public void heartbeat(ChannelHandlerContext ctx, Object msg) {
        this.run(coreService.heartbeat(ctx, msg));
    }

    // access channel read
    @Override
    public void accessChannelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        this.run(coreService.accessChannelRead0(ctx, msg));
    }

    //校验token
//    public void verifyToken(ChannelHandlerContext ctx, FullHttpRequest request) {
//        this.run(coreService.verifyToken(ctx, request));
//    }

    //业务处理
    @Override
    public void doBusiness(ChannelHandlerContext ctx, TextWebSocketFrame msg, ReqEntity reqEntity) {
        this.run(coreService.CmdRouter(ctx, msg, reqEntity));
    }

    //当玩家掉线离开时处理业务
    @Override
    public void handlerRemoved(String uid) {
        this.run(coreService.handlerRemoved(uid));
    }
}
