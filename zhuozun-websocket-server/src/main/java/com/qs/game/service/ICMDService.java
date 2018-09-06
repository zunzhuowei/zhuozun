package com.qs.game.service;

import com.qs.game.model.base.ReqEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * Created by zun.wei on 2018/9/6 15:03.
 * Description: 命令业务基层类
 */
public interface ICMDService {

    /**
     *  执行命令
     * @param ctx ChannelHandlerContext
     * @param msg TextWebSocketFrame
     * @param reqEntity ReqEntity
     * @return Runnable
     */
    Runnable execute(ChannelHandlerContext ctx, TextWebSocketFrame msg, ReqEntity reqEntity);

}
