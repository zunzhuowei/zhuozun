package com.qs.game.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.ChannelInputShutdownReadComplete;
import io.netty.handler.ssl.SslHandshakeCompletionEvent;
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
        if (evt instanceof IdleStateEvent) { //心跳事件
            log.info("====>Heartbeat: greater than {}", 180);
            ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
                    .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        } else if (evt instanceof SslHandshakeCompletionEvent) { //ssl 事件
            log.info("HeartbeatHandler userEventTriggered SslHandshakeCompletionEvent --::{}", evt);
        } else if (evt instanceof ChannelInputShutdownReadComplete) {//频道已关闭
            log.info("HeartbeatHandler userEventTriggered ChannelInputShutdownReadComplete --::{}", evt);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

}
