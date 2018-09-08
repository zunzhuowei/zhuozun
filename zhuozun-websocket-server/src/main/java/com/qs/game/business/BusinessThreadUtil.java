package com.qs.game.business;

import com.qs.game.model.base.ReqEntity;
import com.qs.game.service.ICoreService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 业务线程工具类
 */
@Slf4j
@Component
public class BusinessThreadUtil {

    @Autowired
    private ICoreService coreService;

    private static final ExecutorService executor =
            new ThreadPoolExecutor(12, 16, 0L,
                    TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(100000));//CPU核数4-10倍

    public static ExecutorService getExecutor() {
        return executor;
    }

    //启动线程
    private void run(Runnable runnable) {
        if (Objects.nonNull(runnable)) executor.submit(runnable);
    }

    // tcp握手
    public void handshake(ChannelHandlerContext ctx, Object evt) {
        this.run(coreService.handshake(ctx, evt));
    }

    // 处理客户端的心跳
    public void heartbeat(ChannelHandlerContext ctx, Object msg) {
        this.run(coreService.heartbeat(ctx, msg));
    }

    // access channel read
    public void accessChannelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        this.run(coreService.accessChannelRead0(ctx, msg));
    }

    //校验token
//    public void verifyToken(ChannelHandlerContext ctx, FullHttpRequest request) {
//        this.run(coreService.verifyToken(ctx, request));
//    }

    //业务处理
    public void doBusiness(ChannelHandlerContext ctx, TextWebSocketFrame msg, ReqEntity reqEntity) {
        this.run(coreService.CmdRouter(ctx, msg, reqEntity));
    }

    //当玩家掉线离开时处理业务
    public void handlerRemoved(String uid) {
        this.run(coreService.handlerRemoved(uid));
    }
}
