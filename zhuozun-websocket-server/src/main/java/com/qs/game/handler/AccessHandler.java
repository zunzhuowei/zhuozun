package com.qs.game.handler;

import com.qs.game.common.CMD;
import com.qs.game.common.ERREnum;
import com.qs.game.common.Global;
import com.qs.game.model.base.ReqEntity;
import com.qs.game.model.base.ReqErrEntity;
import com.qs.game.model.base.RespEntity;
import com.qs.game.utils.AccessUtils;
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

import java.util.Objects;


@Slf4j
@ChannelHandler.Sharable
@Component
@Qualifier("accessHandler")
public class AccessHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    private Global global;

    private final boolean autoRelease = true;

//    public AccessHandler(Global global) {
//        this.global = global;
//        this.autoRelease = true;
//    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            WebSocketServerProtocolHandler.HandshakeComplete complete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            log.info("AccessHandler userEventTriggered handshake evt = {},|{},|{}",
                    ((WebSocketServerProtocolHandler.HandshakeComplete) evt).requestHeaders(),
                    ((WebSocketServerProtocolHandler.HandshakeComplete) evt).requestUri(),
                    ((WebSocketServerProtocolHandler.HandshakeComplete) evt).selectedSubprotocol());
            // 获取token中的签名秘钥返回给客户端
            String sKey =  ctx.channel().attr(Global.attrSkey).get();
            ctx.channel().writeAndFlush(new TextWebSocketFrame(
                    RespEntity.getBuilder().setCmd(CMD.HAND_SHAKE)
                            .setErr(ERREnum.SUCCESS).setContent(sKey).buildJsonStr()
            ));

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
        log.info("Client: {} 在线", channel.remoteAddress());
        ctx.fireChannelActive();
    }



    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        ctx.fireChannelInactive();
        String uid = ctx.channel().attr(Global.attrUid).get();
        log.info("Client: {} : {} 掉线", channel.remoteAddress(), uid);
        global.delChannelFromGroup(uid, channel); //掉线剔除
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //global.sendMsg2All(new TextWebSocketFrame("[SERVER] - "  + channel.remoteAddress() + " 加入"));
//        String uid = ctx.channel().attr(Global.attrUid).get();
//        global.add2ChannelGroup(uid, channel);//加入在线组
        log.info("Client: {} 加入", channel.remoteAddress());
    }



    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        //global.sendMsg2All(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 离开"));
        String uid = ctx.channel().attr(Global.attrUid).get();
        global.delChannelFromGroup(uid, incoming);
        log.info("Client: {} : {} 离开", incoming.remoteAddress(), uid);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //1）消息验证
        String msgText = msg.text();
        ReqErrEntity reqErrEntity = AccessUtils.checkAndGetReqEntity(msgText);
        ERREnum errEnum = reqErrEntity.getErrEnum();
        ReqEntity reqEntity = reqErrEntity.getReqEntity();
        switch (errEnum) {
            case SUCCESS: //成功通过请求
            {
                Integer cmd = reqEntity.getCmd();
                if (CMD.LOGIN.VALUE.equals(cmd)) { //登录成功添加到在线组
                    global.add2ChannelGroup(reqErrEntity.getuId(), ctx.channel());
                }
                ctx.channel().attr(Global.attrToken).set(reqEntity.getToken()); //在channel中设置attr
                ctx.channel().attr(Global.attrUid).set(String.valueOf(reqErrEntity.getuId())); //userId
                ctx.fireChannelRead(msg.retain());//msg.retain() 保留msg到下一个handler中处理
                break;
            }
            default:
            {
                ctx.channel().writeAndFlush(new TextWebSocketFrame(
                        RespEntity.getBuilder()
                                .setCmd(Objects.nonNull(reqEntity) ? reqEntity.getCmd() : null)
                                .setErr(errEnum).setContent(msgText).buildJsonStr()
                ));
                ctx.channel().close();
                break;
            }
        }

        //2）取消消息验证
        //ctx.fireChannelRead(msg.retain());//msg.retain() 保留msg到下一个handler中处理
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("AccessHandler channelRead --:: {},{}", ctx, msg);
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
