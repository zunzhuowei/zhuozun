package com.qs.game.service.impl;

import com.qs.game.common.CMD;
import com.qs.game.common.CommandService;
import com.qs.game.common.ERREnum;
import com.qs.game.model.base.ReqEntity;
import com.qs.game.model.base.RespEntity;
import com.qs.game.service.ILoginCMDService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by zun.wei on 2018/9/6 13:49.
 * Description: 登录命令业务接口实现类
 */
@Slf4j
@CommandService(CMD.LOGIN)
public class LoginCMDServiceImpl implements ILoginCMDService {


    @Override
    public Runnable execute(ChannelHandlerContext ctx, TextWebSocketFrame msg, ReqEntity reqEntity) {
        Integer cmd = reqEntity.getCmd();
        return () -> ctx.writeAndFlush(new TextWebSocketFrame(
                RespEntity.getBuilder().setCmd(cmd).setErr(ERREnum.SUCCESS).buildJsonStr()));
    }


}
