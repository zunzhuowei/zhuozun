package com.qs.game.core.impl;

import com.qs.game.common.ERREnum;
import com.qs.game.common.game.CMD;
import com.qs.game.common.game.CommandService;
import com.qs.game.common.game.KunType;
import com.qs.game.common.netty.Global;
import com.qs.game.core.ICommonService;
import com.qs.game.core.IMergeCMDService;
import com.qs.game.core.IThreadService;
import com.qs.game.model.base.ReqEntity;
import com.qs.game.model.base.RespEntity;
import com.qs.game.model.game.Pool;
import com.qs.game.model.game.PoolCell;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.util.stream.Collectors.toList;

/**
 * Created by zun.wei on 2018/9/9.
 * 合并鲲命令接口实现类
 */
@Slf4j
@CommandService(CMD.MERGE)
public class MergeCMDService implements IMergeCMDService {


    @Autowired
    private ICommonService commonService;

    @Autowired
    private Global global;


    @Override
    public Runnable execute(ChannelHandlerContext ctx, TextWebSocketFrame msg, ReqEntity reqEntity) {
        Future future = IThreadService.executor.submit(() -> {
            Integer cmd = reqEntity.getCmd();
            String mid = this.getPlayerId(ctx); //管道中的用户mid
            Map<String, Object> params = reqEntity.getParams();

            Integer fromIndex = commonService.getAndCheckRequestNo(this.getClass(), "from", cmd, mid, params);
            Integer toIndex = commonService.getAndCheckRequestNo(this.getClass(), "to", cmd, mid, params);
            if (Objects.isNull(fromIndex) || Objects.isNull(toIndex)) return;

            //获取玩家的鲲池
            Pool pool = commonService.getAndCheckPool(this.getClass(), cmd, mid);
            if (Objects.isNull(pool)) return;

            List<PoolCell> poolCells = pool.getPoolCells();

            PoolCell fromCell = poolCells.get(fromIndex);
            PoolCell toCell = poolCells.get(toIndex);
            int fromType = fromCell.getKuns().getType();
            int toType = toCell.getKuns().getType();
            //校验鲲的类型是否一致,并判断是否存在
            if (fromType != toType || fromType < 1) {
                log.info("MergeCMDService execute fromType not equals toType !");
                String resultStr = RespEntity.getBuilder().setCmd(cmd)
                        .setErr(ERREnum.ILLEGAL_REQUEST_3).buildJsonStr();
                global.sendMsg2One(resultStr, mid);
                return ;
            }
            int fromWork = fromCell.getKuns().getWork();
            int toWork = toCell.getKuns().getWork();
            // 校验鲲是否在鲲池中，如果在海里则不可以合并
            if (fromWork != 0 || toWork != 0) {
                log.info("MergeCMDService execute work = {}", fromWork, toWork);
                String resultStr = RespEntity.getBuilder().setCmd(cmd)
                        .setErr(ERREnum.ILLEGAL_REQUEST_3).buildJsonStr();
                global.sendMsg2One(resultStr, mid);
                return ;
            }

            //合并类型
            int mergeType = KunType.mergeType(fromType);
            poolCells = poolCells.stream()
                    .peek(poolCell -> {
                        if (Objects.equals(poolCell.getNo(), fromIndex)) {
                            poolCell.getKuns().setType(0);
                        }
                        if (Objects.equals(poolCell.getNo(), toIndex)) {
                            poolCell.getKuns().setType(mergeType);
                        }
                    }).collect(toList());

            //保存鲲池到缓存和内存
            commonService.savePool2CacheAndMemory(mid, pool.setPoolCells(poolCells));

            //获取玩家鲲池
            Map<String, Object> content = new HashMap<>();
            content.put("no", toCell.getNo());
            content.put("type", mergeType);
            String resultStr = RespEntity.getBuilder().setCmd(cmd).setErr(ERREnum.SUCCESS).setContent(content).buildJsonStr();
            global.sendMsg2One(resultStr, mid);
        });
        try {
            Object o = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return () -> ReferenceCountUtil.release(msg);
    }



}
