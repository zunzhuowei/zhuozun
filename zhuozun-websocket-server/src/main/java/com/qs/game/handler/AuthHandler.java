package com.qs.game.handler;

import com.qs.game.common.Global;
import com.qs.game.model.ReqEntity;
import com.qs.game.utils.AccessUtils;
import com.qs.game.utils.HandlerUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.SslHandshakeCompletionEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;


@Slf4j
@ChannelHandler.Sharable
public class AuthHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private Global global;

    private final boolean autoRelease;

    public AuthHandler(Global global) {
        this.global = global;
        this.autoRelease = true;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            WebSocketServerProtocolHandler.HandshakeComplete complete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            log.info("AuthHandler userEventTriggered handshake evt = {},|{},|{}",
                    ((WebSocketServerProtocolHandler.HandshakeComplete) evt).requestHeaders(),
                    ((WebSocketServerProtocolHandler.HandshakeComplete) evt).requestUri(),
                    ((WebSocketServerProtocolHandler.HandshakeComplete) evt).selectedSubprotocol());

            ctx.pipeline().remove(HttpRequestHandler.class);//其除掉，因为后面不会接收任何http请求
            //global.sendMsg2All(new TextWebSocketFrame("Client " + ctx.channel() + " joined"));//发消息给全组成员
        } else if (evt instanceof SslHandshakeCompletionEvent) {
            ctx.pipeline().remove(SslHandler.class);//其除掉，因为后面不会接收任何http请求

        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("Client: {} 在线",channel.remoteAddress());
        //ctx.fireChannelActive();
    }



    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("Client: {} 掉线",channel.remoteAddress());
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        global.sendMsg2All(new TextWebSocketFrame("[SERVER] - "  + channel.remoteAddress() + " 加入"));
        global.add2ChannelGroup(channel);//加入在线组
        log.info("Client: {} 加入",channel.remoteAddress());
    }



    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        global.sendMsg2All(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 离开"));
        log.info("Client: {} 离开",incoming.remoteAddress());
        global.delChannelFromGroup(incoming);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //1）消息验证
       /* String msgText = msg.text();
        ReqEntity reqEntity = AccessUtils.checkAngGetReqEntity(msgText);
        if (Objects.isNull(reqEntity)) {
            ctx.channel().close();
        } else {
            ctx.fireChannelRead(msg.retain());//msg.retain() 保留msg到下一个handler中处理
        }*/

        //2）取消消息验证
        ctx.fireChannelRead(msg.retain());//msg.retain() 保留msg到下一个handler中处理
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("AuthHandler channelRead --:: {},{}", ctx, msg);
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
