package com.qs.game.handler;

import com.qs.game.common.Global;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    private final String wsUri;

    private final boolean autoRelease;

    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
        this.autoRelease = true;
    }


    /*@Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (wsUri.equalsIgnoreCase(request.uri())) {
            ctx.fireChannelRead(request.retain());
        } else {
            if (HttpUtil.is100ContinueExpected(request)) {
                send100Continue(ctx);
            }
        }
    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String srcUir = request.uri();
        String uri = StringUtils.substringBefore(srcUir, "?");
        if (wsUri.equalsIgnoreCase(uri)) {
            //1 token 认证
            QueryStringDecoder query = new QueryStringDecoder(request.uri());
            Map<String, List<String>> map = query.parameters();
            List<String> tokens = map.get("token");
            if (Objects.isNull(tokens) || tokens.isEmpty()) {//关闭连接
                ctx.close();
                ReferenceCountUtil.release(request);
            } else {
                ctx.channel().attr(Global.atrrToken).getAndSet(tokens.get(0));
                //一定要把原请求uri后面的参数去掉，否则不能完成握手.
                FullHttpRequest retain = request.setUri(uri).retain();
                ctx.fireChannelRead(retain);
            }

            //2 不认证token
            //FullHttpRequest retain = request.setUri(uri).retain();
            //ctx.fireChannelRead(retain);
        } else {
            ctx.close();
            ReferenceCountUtil.release(request);
        }
    }


    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        Channel channel = ctx.channel();
        log.error("Client: {} 异常!",channel.remoteAddress());
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        boolean release = true;
        try {
            if (acceptInboundMessage(msg)) {
                channelRead0(ctx, (FullHttpRequest)msg);
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

}
