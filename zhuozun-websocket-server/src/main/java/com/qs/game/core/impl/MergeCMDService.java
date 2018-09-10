package com.qs.game.core.impl;

import com.qs.game.common.*;
import com.qs.game.common.game.CMD;
import com.qs.game.common.game.CommandService;
import com.qs.game.common.game.KunType;
import com.qs.game.common.netty.Global;
import com.qs.game.core.IThreadService;
import com.qs.game.model.base.ReqEntity;
import com.qs.game.model.base.RespEntity;
import com.qs.game.model.game.Kuns;
import com.qs.game.model.game.Pool;
import com.qs.game.model.game.PoolCell;
import com.qs.game.core.ICommonService;
import com.qs.game.core.IMergeCMDService;
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
            if (Objects.isNull(params)) {
                log.info("MergeCMDServiceImpl execute params is null !");
                return;
            }
            String from = Objects.isNull(params.get("from")) ? null : params.get("from").toString();
            String to = Objects.isNull(params.get("to")) ? null : params.get("to").toString();
            if (Objects.isNull(from) || Objects.isNull(to)) {
                log.info("MergeCMDServiceImpl execute from or to is null !");
                return;
            }

            Pool pool = commonService.getPlayerKunPool(mid);
            if (Objects.isNull(pool)) {
                log.info("MergeCMDServiceImpl execute pool is null !");
                return;
            }
            List<PoolCell> poolCells = pool.getPoolCells();
            int fromIndex = IntUtils.str2Int(from);
            int toIndex = IntUtils.str2Int(to);
            PoolCell fromCell = poolCells.get(fromIndex);
            PoolCell toCell = poolCells.get(toIndex);
            int fromType = fromCell.getKuns().getType();
            int toType = toCell.getKuns().getType();
            //校验鲲的类型是否一致
            if (fromType != toType) {
                log.info("MergeCMDServiceImpl execute fromType not equals toType !");
                String resultStr = RespEntity.getBuilder().setCmd(cmd)
                        .setErr(ERREnum.ILLEGAL_REQUEST_3).buildJsonStr();
                global.sendMsg2One(resultStr, mid);
                return ;
            }
            int fromWork = fromCell.getKuns().getWork();
            int toWork = toCell.getKuns().getWork();
            // 校验鲲是否在鲲池中，如果在海里则不可以合并
            if (fromWork != 0 || toWork != 0) {
                log.info("MergeCMDServiceImpl execute work = {}", fromWork, toWork);
                String resultStr = RespEntity.getBuilder().setCmd(cmd)
                        .setErr(ERREnum.ILLEGAL_REQUEST_3).buildJsonStr();
                global.sendMsg2One(resultStr, mid);
                return ;
            }

            int mergeType = KunType.mergeType(fromType);
            poolCells.remove(fromIndex); //移除合并来源
            poolCells.remove(toIndex);//移除合并目的
            poolCells.add(toCell.setKuns(toCell.getKuns().setType(mergeType)));//添加合并后的结果到池中

            //保存鲲池
            commonService.savePool(mid, pool.setPoolCells(poolCells));

            //TODO 要不要去维护这次合并前产生的金币到持久化呢？

            //获取玩家鲲池
            Map<String, Object> content = new HashMap<>();
            //content.put("pool", JSONObject.toJSONString(poolCells)); //玩家鲲池
            //content.put("gold", loginCMDService.getPlayerGold(mid)); //玩家金币
            //content.put("goldSpeed", loginCMDService.getPlayerGoldSpeedByMid(mid)); //玩家产金币速度
            content.put("poolCell", toCell.setKuns(new Kuns().setType(mergeType).setTime(0).setWork(0)));
            String resultStr = RespEntity.getBuilder().setCmd(cmd).setErr(ERREnum.SUCCESS).setContent(content).buildJsonStr();
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
