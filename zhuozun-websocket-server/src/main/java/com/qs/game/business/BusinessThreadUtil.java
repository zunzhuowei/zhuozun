package com.qs.game.business;

import com.qs.game.common.CmdValue;
import com.qs.game.common.ERREnum;
import com.qs.game.common.Global;
import com.qs.game.model.base.ReqEntity;
import com.qs.game.model.base.RespEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.*;

/**
 * 业务线程工具类
 */
@Slf4j
@Component
public class BusinessThreadUtil {

    private static final ExecutorService executor =
            new ThreadPoolExecutor(12, 16, 0L,
                    TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(100000));//CPU核数4-10倍


    //业务处理
    public void doBusiness(ChannelHandlerContext ctx, TextWebSocketFrame msg, ReqEntity reqEntity) {
        Runnable runnable = this.CmdRouter(ctx, msg, reqEntity);
        if (Objects.nonNull(runnable)) executor.submit(runnable);
    }


    // 根据命令处理不同的业务
    private Runnable CmdRouter(ChannelHandlerContext ctx, TextWebSocketFrame msg, ReqEntity reqEntity) {
        Runnable runnable = null;
        Integer cmd = reqEntity.getCmd(); //请求命令
        String uid = ctx.channel().attr(Global.attrUid).get(); //管道中的用户mid
        log.info("BusinessThreadUtil doBusiness uid ---::{}", uid);
        switch (cmd) {
            case CmdValue.LOGIN: //登录
            {
                runnable = () -> ctx.writeAndFlush(new TextWebSocketFrame(
                        RespEntity.getBuilder().setCmd(cmd).setErr(ERREnum.SUCCESS).setContent(msg.text()).buildJsonStr()));
                break;
            }
            case CmdValue.LOGOUT: //登出
            {

                break;
            }
            default:
                log.warn("{} cmd is not exist ~!", cmd);
        }
        return runnable;
    }

}
