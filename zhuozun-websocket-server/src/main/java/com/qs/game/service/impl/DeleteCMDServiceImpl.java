package com.qs.game.service.impl;

import com.qs.game.business.BusinessThreadUtil;
import com.qs.game.common.CMD;
import com.qs.game.common.CommandService;
import com.qs.game.common.Global;
import com.qs.game.model.base.ReqEntity;
import com.qs.game.service.IDeleteCMDService;
import com.qs.game.service.ILoginCMDService;
import com.qs.game.service.IWorkCMDService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by zun.wei on 2018/9/9.
 * 删除一个对象命令接口实现类
 */
@Slf4j
@CommandService(CMD.DELETE)
public class DeleteCMDServiceImpl implements IDeleteCMDService {


    @Autowired
    private ILoginCMDService loginCMDService;

    @Autowired
    private Global global;


    @Override
    public Runnable execute(ChannelHandlerContext ctx, TextWebSocketFrame msg, ReqEntity reqEntity) {
        Future future = BusinessThreadUtil.getExecutor().submit(() -> {
            Integer cmd = reqEntity.getCmd();
            String mid = this.getPlayerId(ctx); //管道中的用户mid
            Map<String, Object> params = reqEntity.getParams();

        });
        try {
            Object o = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }



}
