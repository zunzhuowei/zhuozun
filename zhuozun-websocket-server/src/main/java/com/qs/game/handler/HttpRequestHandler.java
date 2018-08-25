package com.qs.game.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.qs.game.common.Constants.*;

@Slf4j
@Component
@Qualifier("httpRequestHandler")
@ChannelHandler.Sharable
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        log.info("===========> {}", msg.uri());
        doHandlerHttpRequest(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * wetsocket第一次连接握手
     *
     * @param ctx
     * @param msg
     */
    private void doHandlerHttpRequest(ChannelHandlerContext ctx, HttpRequest msg) {
        // http 解码失败
        boolean success = msg.decoderResult().isSuccess();
        String isUpgrade = msg.headers().get(Upgrade);
        boolean header = WEBSOCKET.equals(isUpgrade);
        if (!success || (!header)) {
            sendHttpResponse(ctx, (FullHttpRequest) msg, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
        }
        //可以获取msg的uri来判断
        String uri = msg.uri();
        if (!uri.equals(URI)) {
            ctx.close();
        }
        Channel channel = ctx.channel();
        channel.attr(AttributeKey.valueOf("type")).set(uri);
        channel.attr(CHANNEL_TOKEN_KEY).getAndSet(uri);

        //可以通过url获取其他参数
        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(
                "ws://" + msg.headers().get("Host") + URI, null, false
        );
        WebSocketServerHandshaker handshaker = factory.newHandshaker(msg);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channel, new DefaultChannelPromise(channel));
            return;
        }
        //进行连接
        handshaker.handshake(channel, (FullHttpRequest) msg);
        //可以做其他处理
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
        int code = res.status().code();
        int ok = HttpStatus.OK.value();
        if (code != ok) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpUtil.isKeepAlive(req) || code != ok) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }


}
