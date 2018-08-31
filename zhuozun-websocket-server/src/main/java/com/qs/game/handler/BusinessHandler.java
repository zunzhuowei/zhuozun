package com.qs.game.handler;

import com.qs.game.common.Global;
import com.qs.game.utils.HandlerUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * binary handler
 *
 */
@Slf4j
@ChannelHandler.Sharable
public class BusinessHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private Global global;

    public BusinessHandler(Global global) {
        this.global = global;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("BusinessHandler channelRead0 {},{}", ctx, msg);
        Channel incoming = ctx.channel();
        String incomingId = HandlerUtils.getClientShortIdByChannel(incoming);
        String token = ctx.channel().attr(Global.attrToken).get(); //在channel中设置attr
        String uid = ctx.channel().attr(Global.attrUid).get(); //userId

        for (Channel channel : Global.getChannelGroup()) {
            String groupClientId = HandlerUtils.getClientShortIdByChannel(channel);
            if (StringUtils.equals(incomingId, groupClientId)) {
                channel.writeAndFlush(new TextWebSocketFrame(msg.text()));
            }else {
                //发送给指定的
                channel.writeAndFlush(new TextWebSocketFrame(msg.text()));

                StringBuffer sb = new StringBuffer();
                sb.append(incoming.remoteAddress()).append("->").append(msg.text());
                log.info("channelRead02222 :: {}", sb.toString());
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("BusinessHandler ctx = [" + ctx + "]");
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        log.warn("Client: {} 异常,{}", incoming.remoteAddress(), cause.toString());
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

}
