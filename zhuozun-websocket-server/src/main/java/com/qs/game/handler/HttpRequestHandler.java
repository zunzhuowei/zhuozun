package com.qs.game.handler;

import com.qs.game.auth.JWTEntity;
import com.qs.game.auth.JWTUtils;
import com.qs.game.cache.CacheKey;
import com.qs.game.common.Constants;
import com.qs.game.common.netty.Global;
import com.qs.game.constant.StrConst;
import com.qs.game.model.base.RespEntity;
import com.qs.game.service.IRedisService;
import com.qs.game.utils.SpringBeanUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.qs.game.common.game.CMD.CLOSE_SERVER;
import static com.qs.game.common.ERREnum.ILLEGAL_REQUEST_2;

@Slf4j
@ChannelHandler.Sharable
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    private final String wsUri;

    private final boolean autoRelease;

    private static IRedisService redisService;

    public HttpRequestHandler() {
        this.wsUri = StrConst.SLASH;
        this.autoRelease = true;
        if (Objects.isNull(redisService))
            redisService = SpringBeanUtil.getBean("redisService", IRedisService.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String srcUir = request.uri();
        String uri = StringUtils.substringBefore(srcUir, Constants.QUESTION_MARK);

        if (wsUri.equalsIgnoreCase(uri)) {
            String isClose = redisService.get(CacheKey.Redis.CLOSE_GAME_SERVER.KEY);
            //判断是否停服
            if (StringUtils.equals(StrConst.ONE, isClose)) {
                ctx.channel().writeAndFlush(new TextWebSocketFrame(
                        RespEntity.getBuilder()
                                .setCmd(CLOSE_SERVER)
                                .setErr(ILLEGAL_REQUEST_2)
                                .buildJsonStr()
                ));
                ctx.channel().close();
            } else {
                //1 token 认证,如果认证不过，则不进行websocket通信。
                this.authenticationToken(ctx, request, uri);

                //2 不认证token
                //FullHttpRequest retain = request.setUri(uri).retain();
                //ctx.fireChannelRead(retain);
            }
        } else {
            ctx.close();
        }
    }

    //token认证
    private void authenticationToken(ChannelHandlerContext ctx, FullHttpRequest request, String uri) {
        QueryStringDecoder query = new QueryStringDecoder(request.uri());
        Map<String, List<String>> map = query.parameters();
        log.info("HttpRequestHandler channelRead0 map --:{}", map);
        List<String> tokens = map.get(Constants.TOKEN);
        List<String> uids = map.get(Constants.USER_ID);
        //校验是否为空参数
        boolean isBad = Objects.isNull(tokens) || tokens.isEmpty()
                || Objects.isNull(uids) || uids.isEmpty();
        if (isBad) {//关闭连接
            log.warn("HttpRequestHandler channelRead0 isBad = true");
            ctx.channel().writeAndFlush(new DefaultFullHttpResponse
                            (HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            ctx.close();
        } else {
            String token = tokens.get(0); //参数中的token
            String uid = uids.get(0); //参数中的uid
            JWTEntity jwtEntity = JWTUtils.getEntityByToken(token);
            if (Objects.isNull(jwtEntity)) {
                log.warn("HttpRequestHandler channelRead0 jwtEntity is null,maybe token is illegal");
                ctx.channel().writeAndFlush(new DefaultFullHttpResponse
                        (HttpVersion.HTTP_1_1, HttpResponseStatus.METHOD_NOT_ALLOWED));
                ctx.close();
                return;
            }
            Date expDate = jwtEntity.getExpDate();
            //校验token是否合法
            if (Objects.isNull(expDate)) {
                log.warn("HttpRequestHandler channelRead0 expDate is null,maybe token is illegal");
                ctx.channel().writeAndFlush(new DefaultFullHttpResponse
                        (HttpVersion.HTTP_1_1, HttpResponseStatus.METHOD_NOT_ALLOWED));
                ctx.close();
                return;
            }
            long nowTime = new Date().getTime();
            long expTime = expDate.getTime();
            //校验token过期时间
            if (expTime < nowTime) {
                log.warn("HttpRequestHandler channelRead0 expDate is exp");
                ctx.channel().writeAndFlush(new DefaultFullHttpResponse
                        (HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_ACCEPTABLE));
                ctx.close();
                return;
            }
            //校验token是否是登录用户产生
            String cacheToken = redisService.get(CacheKey.RedisPrefix.TOKEN_PREFIX.KEY + uid);
            if (StringUtils.isBlank(cacheToken)) {
                log.warn("HttpRequestHandler channelRead0 cacheToken is null, uid:{}", uid);
                ctx.channel().writeAndFlush(new DefaultFullHttpResponse
                        (HttpVersion.HTTP_1_1, HttpResponseStatus.UNAUTHORIZED));
                ctx.close();
                return;
            }
            //校验cache 中的token 和传过来的token是否一致
            if (!StringUtils.equals(cacheToken, token)) {
                log.warn("HttpRequestHandler channelRead0 cacheToken not equals token, uid:{}", uid);
                ctx.channel().writeAndFlush(new DefaultFullHttpResponse
                        (HttpVersion.HTTP_1_1, HttpResponseStatus.PROXY_AUTHENTICATION_REQUIRED));
                ctx.close();
                return;
            }
            //校验token中的uid与参数中的uid是否一致
            Long tokenUid = jwtEntity.getUid();
            if (!StringUtils.equals(tokenUid + StrConst.EMPTY_STR, uid)) {
                log.warn("HttpRequestHandler channelRead0 tokenUid not equals param uid , uid:{}", uid);
                ctx.channel().writeAndFlush(new DefaultFullHttpResponse
                        (HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_ACCEPTABLE));
                ctx.close();
                return;
            }
            //把attr放入context中到下一个handler中处理
            //ctx.channel().attr(Global.attrSkey).set(jwtEntity.getSKey()); //signKey
            ctx.channel().attr(Global.attrUid).set(String.valueOf(tokenUid)); //user_id

            //一定要把原请求uri后面的参数去掉，否则不能完成握手.
            FullHttpRequest retain = request.setUri(uri).retain();
            ctx.fireChannelRead(retain);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        log.warn("Client: {} 异常! --::{}", channel.remoteAddress(), cause.toString());
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
