package com.qs.game.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * binary handler
 *
 */
@Slf4j
public class BinaryWebSocketHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
        log.info("BinaryWebSocketHandler channelRead0 {},{}", ctx, msg);

    }

}
