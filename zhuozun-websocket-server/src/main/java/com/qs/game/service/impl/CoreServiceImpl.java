package com.qs.game.service.impl;

import com.qs.game.common.*;
import com.qs.game.constant.StrConst;
import com.qs.game.handler.HttpRequestHandler;
import com.qs.game.model.base.ReqEntity;
import com.qs.game.model.base.ReqErrEntity;
import com.qs.game.model.base.RespEntity;
import com.qs.game.service.ICMDService;
import com.qs.game.service.ICoreService;
import com.qs.game.utils.AccessUtils;
import com.qs.game.utils.HeartBeatUtils;
import com.qs.game.utils.SpringBeanUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 核心业务层接口实现类
 */
@Slf4j
@Service
public class CoreServiceImpl implements ICoreService {


    @Autowired
    private Global global;

    //命令路由业务集合
    private static final Map<Integer, ICMDService> COMMAND_CMD_SERVICE = new ConcurrentHashMap<>();

    @Override
    public Runnable CmdRouter(ChannelHandlerContext ctx, TextWebSocketFrame msg, ReqEntity reqEntity) {
        Integer cmd = reqEntity.getCmd(); //请求命令
        String uid = ctx.channel().attr(Global.attrUid).get(); //管道中的用户mid
        log.info("BusinessThreadUtil CmdRouter uid,cmd ---::{},{}", uid, cmd);

        if (COMMAND_CMD_SERVICE.isEmpty()) {
            Map<String, ICMDService> service = SpringBeanUtil.getCMDServiceBeans();
            service.entrySet().parallelStream()
                    .filter(e -> e.getValue().getClass().isAnnotationPresent(CommandService.class))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toSet())
                    .forEach(e -> {
                        CommandService command = e.getClass().getAnnotation(CommandService.class);
                        CMD reqCmd = command.value();
                        COMMAND_CMD_SERVICE.put(reqCmd.VALUE, e);
                    });
        }

        //执行业务逻辑
        ICMDService icmdService = COMMAND_CMD_SERVICE.get(cmd);
        return Objects.isNull(icmdService) ? null : icmdService.execute(ctx, msg, reqEntity);
    }

    @Override
    public Runnable handshake(ChannelHandlerContext ctx, Object evt) {
        return () -> {
//            WebSocketServerProtocolHandler.HandshakeComplete complete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
//            log.info("AccessHandler userEventTriggered handshake evt = {},|{},|{}",
//                    ((WebSocketServerProtocolHandler.HandshakeComplete) evt).requestHeaders(),
//                    ((WebSocketServerProtocolHandler.HandshakeComplete) evt).requestUri(),
//                    ((WebSocketServerProtocolHandler.HandshakeComplete) evt).selectedSubprotocol());
            // 获取token中的签名秘钥返回给客户端
            //String sKey =  ctx.channel().attr(Global.attrSkey).get();
            ctx.channel().writeAndFlush(new TextWebSocketFrame(
                    RespEntity.getBuilder().setCmd(CMD.HAND_SHAKE)
                            .setErr(ERREnum.SUCCESS).buildJsonStr()
            ));
            ctx.pipeline().remove(HttpRequestHandler.class);//其除掉，因为后面不会接收任何http请求
        };
    }

    @Override
    public Runnable accessChannelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        return () -> {
            //1）消息验证
            String msgText = msg.text();
            ReqErrEntity reqErrEntity = AccessUtils.checkAndGetReqEntity(msgText);
            ERREnum errEnum = reqErrEntity.getErrEnum();
            ReqEntity reqEntity = reqErrEntity.getReqEntity();
            switch (errEnum) {
                case SUCCESS: //成功通过请求
                {
                    String uid = ctx.channel().attr(Global.attrUid).get();
                    log.info("AccessHandler channelRead0 uid ---::{}", uid);
                    Integer cmd = reqEntity.getCmd();
                    if (CMD.LOGIN.VALUE.equals(cmd)) { //登录成功添加到在线组
                        global.add2SessionRepo(uid, ctx);
                    }
                    if (CMD.LOGOUT.VALUE.equals(cmd)) { //退出登录
                        global.delCtxFromSessionRepo(uid);
                        ctx.channel().close();
                        break;
                    }
                    ctx.fireChannelRead(msg.retain());//msg.retain() 保留msg到下一个handler中处理
                    break;
                }
                default: {
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
        };
    }

    @Override
    public Runnable heartbeat(ChannelHandlerContext ctx, Object msg) {
        return () -> {
            String clientMsg = ((TextWebSocketFrame) msg).text();
            if (StrConst.HB.equals(clientMsg)) {
                String uid = ctx.channel().attr(Global.attrUid).get();
                //Global.getSessionRepo().forEach((key, value) -> System.out.println("key = " + key + "  --  " + value));
                log.debug("client request heart beat ---------::{}", clientMsg);
                HeartBeatUtils.heartBeats.put(uid, new Date().getTime());
                //客户端请求心跳
                ctx.writeAndFlush(new TextWebSocketFrame(StrConst.HB));//.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                ReferenceCountUtil.release(msg); //释放资源
            } else {
                ctx.fireChannelRead(((TextWebSocketFrame) msg).retain());
            }
        };
    }


}
