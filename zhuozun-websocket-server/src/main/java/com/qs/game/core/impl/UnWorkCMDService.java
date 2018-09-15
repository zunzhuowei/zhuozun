package com.qs.game.core.impl;

import com.qs.game.common.ERREnum;
import com.qs.game.common.game.CMD;
import com.qs.game.common.game.CommandService;
import com.qs.game.common.netty.Global;
import com.qs.game.core.ICommonService;
import com.qs.game.core.IThreadService;
import com.qs.game.core.IUnWorkCMDService;
import com.qs.game.model.base.ReqEntity;
import com.qs.game.model.base.RespEntity;
import com.qs.game.model.game.Kun;
import com.qs.game.model.game.Kuns;
import com.qs.game.model.game.Pool;
import com.qs.game.model.game.PoolCell;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.util.stream.Collectors.toList;

/**
 * Created by zun.wei on 2018/9/9.
 * 把鲲收回池里命令接口实现类
 */
@Slf4j
@CommandService(CMD.UN_WORK)
public class UnWorkCMDService implements IUnWorkCMDService {


    @Autowired
    private ICommonService commonService;

    @Autowired
    private Global global;


    @Override
    public Runnable execute(ChannelHandlerContext ctx, TextWebSocketFrame msg, ReqEntity reqEntity) {
        IThreadService.executor.execute(() -> {
            Integer cmd = reqEntity.getCmd();
            String mid = this.getPlayerId(ctx); //管道中的用户mid
            Map<String, Object> params = reqEntity.getParams();

            //校验参数是否为空
            Integer noIndex = commonService.getAndCheckRequestNo(this.getClass(), "no", cmd, mid, params);
            if (Objects.isNull(noIndex)) return;

            //获取玩家的鲲池
            Pool pool = commonService.getAndCheckPool(this.getClass(), cmd, mid);
            if (Objects.isNull(pool)) return;

            List<PoolCell> poolCells = pool.getPoolCells();
            //添加金币,并持久化（redis 、 db）
            long nowGold = commonService.updateGoldByNo(mid, poolCells, noIndex);

            poolCells = Optional.ofNullable(poolCells)
                    .map(e -> e.stream().peek(k -> {
                        Integer kNo = k.getNo();
                        if (Objects.equals(noIndex, kNo)) {
                            //把收回来的鲲设置成不工作状态
                            Kuns kuns = new Kuns();
                            BeanUtils.copyProperties(k.getKuns(), kuns);
                            k.setKuns(kuns.setTime(0).setWork(0));
                        }
                    }).collect(toList()))
                    .orElseGet(() -> commonService.getPlayerKunPool(mid).getPoolCells());

            //保存鲲池到缓存和内存
            commonService.savePool2CacheAndMemory(mid, pool.setPoolCells(poolCells));

            Map<String, Object> content = new HashMap<>();
            content.put("no", noIndex);
            content.put("time", 0);
            String resultStr = RespEntity.getBuilder().setCmd(cmd).setContent(content)
                    .setErr(ERREnum.SUCCESS).buildJsonStr();
            global.sendMsg2One(resultStr, mid);
            ReferenceCountUtil.release(msg);
        });
        return null;
    }



}
