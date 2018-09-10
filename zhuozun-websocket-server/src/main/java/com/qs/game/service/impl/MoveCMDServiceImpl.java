package com.qs.game.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qs.game.business.BusinessThreadUtil;
import com.qs.game.common.*;
import com.qs.game.model.base.ReqEntity;
import com.qs.game.model.base.RespEntity;
import com.qs.game.model.sys.Kuns;
import com.qs.game.model.sys.Pool;
import com.qs.game.model.sys.PoolCell;
import com.qs.game.service.ILoginCMDService;
import com.qs.game.service.IMergeCMDService;
import com.qs.game.service.IMoveCMDService;
import com.qs.game.utils.IntUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Created by zun.wei on 2018/9/9.
 * 移动鲲命令接口实现类
 */
@Slf4j
@CommandService(CMD.MOVE)
public class MoveCMDServiceImpl implements IMoveCMDService {


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
