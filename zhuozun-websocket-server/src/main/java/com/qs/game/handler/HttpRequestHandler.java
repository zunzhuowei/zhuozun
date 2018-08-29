package com.qs.game.handler;

import com.qs.game.common.Constants;
import com.qs.game.common.Global;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@ChannelHandler.Sharable
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    private final String wsUri;

    private final boolean autoRelease;

    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
        this.autoRelease = true;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String srcUir = request.uri();
        String uri = StringUtils.substringBefore(srcUir, Constants.QUESTION_MARK);
        if (wsUri.equalsIgnoreCase(uri)) {
            //1 token 认证,如果认证不过，则不进行websocket通信。
            QueryStringDecoder query = new QueryStringDecoder(request.uri());
            Map<String, List<String>> map = query.parameters();
            log.info("HttpRequestHandler channelRead0 map --:{}", map);
            List<String> tokens = map.get(Constants.TOKEN);
            List<String> uid = map.get(Constants.USER_ID);
            //校验是否为空参数
            boolean isBad = Objects.isNull(tokens) || tokens.isEmpty()
                    || Objects.isNull(uid) || uid.isEmpty();
            if (isBad) {//关闭连接
                ctx.channel().writeAndFlush(new DefaultFullHttpResponse
                                (HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
                ctx.close();
            } else {
                //TODO 登录的时候放token到缓存中了，所以此处应该校验缓存中的token是否存在

                //一定要把原请求uri后面的参数去掉，否则不能完成握手.
                FullHttpRequest retain = request.setUri(uri).retain();
                ctx.fireChannelRead(retain);
            }

            //2 不认证token
            //FullHttpRequest retain = request.setUri(uri).retain();
            //ctx.fireChannelRead(retain);
        } else {
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        log.warn("Client: {} 异常! --::{}", channel.remoteAddress(), cause.toString());
        // 当出现异常就关闭连接
        //cause.printStackTrace();
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
