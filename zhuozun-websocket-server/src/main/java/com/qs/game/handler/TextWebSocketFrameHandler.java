package com.qs.game.handler;

import com.qs.game.utils.HandlerUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import static com.qs.game.common.Global.channelGroup;

@Slf4j
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("Client: {} 在线",channel.remoteAddress());
    }



    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("Client: {} 掉线",channel.remoteAddress());
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for (Channel ch : channelGroup) { //遍历发送给所有在线用户
            ch.writeAndFlush(new TextWebSocketFrame("[SERVER] - "  + channel.remoteAddress() + " 加入"));
        }
        channelGroup.add(ctx.channel()); //加入在线组
        log.info("Client: {} 加入",channel.remoteAddress());
    }



    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channelGroup) {
            channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - "  + incoming.remoteAddress() + " 离开"));
        }
        log.info("Client: {} 离开",incoming.remoteAddress());
        channelGroup.remove(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Channel incoming = ctx.channel();
        String incomingId = HandlerUtils.getClientShortIdByChannel(incoming);

        for (Channel channel : channelGroup) {
            String groupClientId = HandlerUtils.getClientShortIdByChannel(channel);
            if (StringUtils.equals(incomingId, groupClientId)) {
                channel.writeAndFlush(new TextWebSocketFrame("[服务器端返回]：" + msg.text()));
            }else {
                //发送给指定的
                channel.writeAndFlush(new TextWebSocketFrame
                        ("[来自客户端的消息]：" + incomingId + " : " + msg.text()));

                StringBuffer sb = new StringBuffer();
                sb.append(incoming.remoteAddress()).append("->").append(msg.text());
                log.info("channelRead0 :: {}", sb.toString());
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        log.error("Client: {} 异常",incoming.remoteAddress());
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

}
