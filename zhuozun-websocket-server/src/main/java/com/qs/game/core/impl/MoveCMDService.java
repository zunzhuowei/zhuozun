package com.qs.game.core.impl;

import com.qs.game.common.ERREnum;
import com.qs.game.common.game.CMD;
import com.qs.game.common.game.CommandService;
import com.qs.game.common.netty.Global;
import com.qs.game.core.ICommonService;
import com.qs.game.core.IMoveCMDService;
import com.qs.game.core.IThreadService;
import com.qs.game.model.base.ReqEntity;
import com.qs.game.model.base.RespEntity;
import com.qs.game.model.game.Pool;
import com.qs.game.model.game.PoolCell;
import com.qs.game.utils.IntUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

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
public class MoveCMDService implements IMoveCMDService {


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
            if (Objects.isNull(params)) {
                log.info("MoveCMDService execute params is null !");
                return;
            }
            String fromNo = Objects.isNull(params.get("from")) ? null : params.get("from").toString();
            String toNo = Objects.isNull(params.get("to")) ? null : params.get("to").toString();
            //校验参数是否为空
            if (Objects.isNull(fromNo) || Objects.isNull(toNo)) {
                log.info("MoveCMDService execute from or to is null !");
                return;
            }

            //获取玩家的鲲池
            Pool pool = commonService.getPlayerKunPool(mid);
            if (Objects.isNull(pool)) {
                log.info("MoveCMDService execute pool is null !");
                return;
            }
            List<PoolCell> poolCells = pool.getPoolCells();
            Integer fromIndex = IntUtils.str2Int(fromNo);
            Integer toIndex = IntUtils.str2Int(toNo);

            if (Objects.isNull(fromIndex) || Objects.isNull(toIndex)) {
                log.info("MoveCMDService execute fromIndex or toIndex is null !");
                return;
            }

            if (fromIndex < 0 || toIndex < 0) {
                log.info("MoveCMDService execute fromIndex or toIndex less than 0 !");
                return;
            }

            PoolCell fromCell = poolCells.get(fromIndex);
            PoolCell toCell = poolCells.get(toIndex);

            //校验原对象单元格是否为空
            if (Objects.isNull(fromCell)) {
                log.info("MoveCMDService execute fromCell is null !");
                return;
            }

            //判断原对象单元格鲲是否在工作
            if (fromCell.getKuns().getWork() > 0) {
                log.info("MoveCMDService execute fromCell.getKuns() less than 1 !");
                return;
            }

            //判断新单元格位置是否为空位置
            if (Objects.isNull(toCell)) {
                poolCells = poolCells.stream().peek(e -> {
                    if (Objects.equals(e.getNo(), fromIndex))
                        e.setNo(toIndex);
                }).collect(Collectors.toList());
                //保存鲲池到缓存和内存
                commonService.savePool2CacheAndMemory(mid, pool.setPoolCells(poolCells));
                return;
            }

            int fromType = fromCell.getKuns().getType();
            int toType = toCell.getKuns().getType();

            // 判断两个单元格的鲲的类型是否一致
            if (fromType == toType) {
                log.info("MoveCMDService execute fromType equals toType !");
                return;
            }

            //判断新单元格鲲是否在工作
            if (toCell.getKuns().getWork() > 0) {
                log.info("MoveCMDService execute toCell.getKuns().getWork() > 0 !");
                return;
            }

            //如果两个单元格鲲类型不一致并且都不在工作则互换位置
            poolCells = poolCells.stream().peek(e -> {
                if (Objects.equals(e.getNo(), fromIndex))
                    e.setNo(toIndex);
                if (Objects.equals(e.getNo(), toIndex))
                    e.setNo(fromIndex);
            }).collect(Collectors.toList());
            //保存鲲池到缓存和内存
            commonService.savePool2CacheAndMemory(mid, pool.setPoolCells(poolCells));

            String resultStr = RespEntity.getBuilder().setCmd(cmd).setErr(ERREnum.SUCCESS).buildJsonStr();
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
