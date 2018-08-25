package com.qs.game.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qs.game.common.Constants;
import com.qs.game.model.ChatMessage;
import com.qs.game.model.UserInfo;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Qualifier("textWebSocketFrameHandler")
@ChannelHandler.Sharable
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    private ChannelGroup channelGroup;

    @Autowired
    public TextWebSocketFrameHandler(ChannelGroup channelGroup) {
        this.channelGroup = channelGroup;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("Event====>{}", evt);

        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            ctx.pipeline().remove(HttpRequestHandler.class);

            //加入当前, 上线人员推送前端，显示用户列表中去
            Channel channel = ctx.channel();
            ChatMessage message = new ChatMessage(null, "");
            channelGroup.writeAndFlush(new TextWebSocketFrame
                    (JSON.toJSONString(message, SerializerFeature.DisableCircularReferenceDetect)));
            channelGroup.add(channel);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Channel channel = ctx.channel();
        String token = channel.attr(Constants.CHANNEL_TOKEN_KEY).get();
        UserInfo from = Constants.onlines.get(token);
        if (from == null) {
            channelGroup.writeAndFlush("OK");
        } else {
            ChatMessage message = new ChatMessage(from, msg.text());
            channelGroup.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message, SerializerFeature.DisableCircularReferenceDetect)));
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("Current channel channelInactive");
        offlines(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("Current channel handlerRemoved");
        offlines(ctx);
    }

    private void offlines(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        String token = channel.attr(Constants.CHANNEL_TOKEN_KEY).get();
        Constants.removeOnlines(token);

        channelGroup.remove(channel);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("=====> {}", cause.getMessage());
        offlines(ctx);
    }


}
