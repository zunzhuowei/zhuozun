package com.qs.game.handler;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 *  pong
 */
@Slf4j
public class PongHandler extends SimpleChannelInboundHandler<PongWebSocketFrame> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PongWebSocketFrame msg) throws Exception {
        log.info("PongHandler channelRead0 {},{}", ctx, msg);
        ctx.channel().writeAndFlush(msg.duplicate()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }

}
