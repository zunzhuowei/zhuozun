package com.qs.game.core.impl;

import com.qs.game.common.ERREnum;
import com.qs.game.common.game.CMD;
import com.qs.game.common.game.CommandService;
import com.qs.game.common.netty.Global;
import com.qs.game.core.ICommonService;
import com.qs.game.core.IThreadService;
import com.qs.game.core.IWorkCMDService;
import com.qs.game.model.base.ReqEntity;
import com.qs.game.model.base.RespEntity;
import com.qs.game.model.game.Pool;
import com.qs.game.model.game.PoolCell;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.util.stream.Collectors.toList;

/**
 * Created by zun.wei on 2018/9/9.
 * 把鲲放到海里命令接口实现类
 */
@Slf4j
@CommandService(CMD.WORK)
public class WorkCMDService implements IWorkCMDService {


    @Autowired
    private Global global;

    @Autowired
    private ICommonService commonService;

    @Override
    public Runnable execute(ChannelHandlerContext ctx, TextWebSocketFrame msg, ReqEntity reqEntity) {
        Future future = IThreadService.executor.submit(() -> {
            Integer cmd = reqEntity.getCmd();
            String mid = this.getPlayerId(ctx); //管道中的用户mid
            Map<String, Object> params = reqEntity.getParams();
            //校验参数是否为空
            Integer noIndex = commonService.getAndCheckKunIndex(this.getClass(), params, "no");
            if (noIndex == null) return;

            //获取玩家的鲲池
            Pool pool = commonService.getAndCheckPool(this.getClass(), mid);
            if (Objects.isNull(pool)) return;

            long nowTime = new Date().getTime() / 1000;

            List<PoolCell> poolCells = pool.getPoolCells();
            poolCells = Optional.ofNullable(poolCells)
                    .map(e -> e.stream().peek(k -> {
                        Integer kNo = k.getNo();
                        if (Objects.equals(noIndex, kNo)) {
                            k.setKuns(k.getKuns().setTime(nowTime).setWork(1));
                        }
                    }).collect(toList()))
                    .orElseGet(() -> commonService.getPlayerKunPool(mid).getPoolCells());

            //保存鲲池到缓存和内存
            commonService.savePool2CacheAndMemory(mid, pool.setPoolCells(poolCells));

            Map<String, Object> content = new HashMap<>();
            content.put("no", noIndex);
            content.put("time", nowTime);
            String resultStr = RespEntity.getBuilder().setCmd(cmd).setContent(content)
                    .setErr(ERREnum.SUCCESS).buildJsonStr();
            global.sendMsg2One(resultStr, mid);
        });
        try {
            Object o = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


}
