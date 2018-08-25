package com.qs.game.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 心跳操作
 */
@Slf4j
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {


    private final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer
            (Unpooled.copiedBuffer("HB", CharsetUtil.UTF_8));

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.warn("HeartbeatHandler userEventTriggered --::{},{}", ctx, evt);
        if (evt instanceof IdleStateEvent) {
            log.info("====>Heartbeat: greater than {}", 180);
            ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
                    .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

}
