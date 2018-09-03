package com.qs.game.business;

import com.qs.game.common.CMD;
import com.qs.game.model.base.ReqEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 业务线程工具类
 */
public class BusinessThreadUtil {

    private static final ExecutorService executor =
            new ThreadPoolExecutor(2, 2, 0L,
                    TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(100000));//CPU核数4-10倍


    //业务处理
    public static void doBusiness(ChannelHandlerContext ctx, TextWebSocketFrame msg, ReqEntity reqEntity) {
        Integer cmd = reqEntity.getCmd();
        Runnable runnable = null;
        if (Objects.equals(CMD.LOGIN.VALUE, cmd)) {
            runnable = () -> ctx.writeAndFlush(new TextWebSocketFrame(msg.text()));
        }

        if (Objects.nonNull(runnable))
            executor.submit(runnable);
    }

}
