package com.qs.game.handler;

import com.qs.game.business.BusinessThreadUtil;
import com.qs.game.common.CMD;
import com.qs.game.common.ERREnum;
import com.qs.game.common.Global;
import com.qs.game.model.base.ReqEntity;
import com.qs.game.model.base.ReqErrEntity;
import com.qs.game.model.base.RespEntity;
import com.qs.game.utils.AccessUtils;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.SslHandshakeCompletionEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Slf4j
@ChannelHandler.Sharable
@Component
@Qualifier("accessHandler")
public class AccessHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    private Global global;

    @Autowired
    private BusinessThreadUtil businessThreadUtil;

    private final boolean autoRelease = true;


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            //            WebSocketServerProtocolHandler.HandshakeComplete complete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
//            log.info("AccessHandler userEventTriggered handshake evt = {},|{},|{}",
//                    ((WebSocketServerProtocolHandler.HandshakeComplete) evt).requestHeaders(),
//                    ((WebSocketServerProtocolHandler.HandshakeComplete) evt).requestUri(),
//                    ((WebSocketServerProtocolHandler.HandshakeComplete) evt).selectedSubprotocol());
            // 获取token中的签名秘钥返回给客户端
            //String sKey =  ctx.channel().attr(Global.attrSkey).get();
//            ctx.channel().writeAndFlush(new TextWebSocketFrame(
//                    RespEntity.getBuilder().setCmd(CMD.HAND_SHAKE)
//                            .setErr(ERREnum.SUCCESS).buildJsonStr()
//            ));
//            ctx.pipeline().remove(HttpRequestHandler.class);
            businessThreadUtil.handshake(ctx, evt);
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
        ctx.fireChannelActive();
    }



    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //ctx.fireChannelInactive();
        String uid = ctx.channel().attr(Global.attrUid).get();
        global.delCtxFromSessionRepo(uid);
        channel.close();
        log.info("Client: {} : {} 掉线", channel.remoteAddress(), uid);
    }


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
        incoming.close();
        log.info("Client: {} : {} 离开", incoming.remoteAddress(), uid);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        businessThreadUtil.accessChannelRead0(ctx, msg);
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
