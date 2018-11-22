package com.qs.game.handler;

import com.qs.game.common.netty.Global;
import com.qs.game.core.IThreadService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.SslHandshakeCompletionEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Slf4j
@ChannelHandler.Sharable
@Component
@Qualifier("accessHandler")
public class AccessHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    private Global global;

    @Autowired
    private IThreadService threadService;

    private final boolean autoRelease = true;


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            threadService.handshake(ctx, evt);
        } else if (evt instanceof SslHandshakeCompletionEvent) {
            ctx.pipeline().remove(SslHandler.class);//其除掉，因为后面不会接收任何http请求
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("Client: {} 在线", channel.remoteAddress());
        //ctx.fireChannelActive();
    }



/*    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String uid = ctx.channel().attr(Global.attrUid).get();
        global.delCtxFromSessionRepo(uid);
        channel.close();
        log.info("Client: {} : {} 掉线", channel.remoteAddress(), uid);
    }*/


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("Client: {} 加入", channel.remoteAddress());
    }



    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        String uid = ctx.channel().attr(Global.attrUid).get();
        global.delCtxFromSessionRepo(uid);
        log.info("Client: {} : {} 离开", incoming.remoteAddress(), uid);
        // channel 关闭的时候需要把玩家内存中的数据刷到缓存 or DB
        threadService.handlerRemoved(uid);
        incoming.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        threadService.accessChannelRead0(ctx, msg);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.debug("AccessHandler channelRead --:: {},{}", ctx, msg);
        boolean release = true;
        try {
            if (acceptInboundMessage(msg)) {
                channelRead0(ctx, (TextWebSocketFrame)msg);
            } else {
                release = false;
                ctx.fireChannelRead(msg);
            }
        } finally {
            if (autoRelease && release) {
                ReferenceCountUtil.release(msg);
            }
        }
    }

    /*
    1. handlerAdded
    2. userEventTriggered
    3. channelRead
    4. channelRead0
    5. handlerRemoved
     */
}
