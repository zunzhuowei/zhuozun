package com.qs.game.core.impl;

import com.alibaba.fastjson.JSONObject;
import com.qs.game.common.*;
import com.qs.game.common.game.CMD;
import com.qs.game.common.game.CommandService;
import com.qs.game.common.game.KunGold;
import com.qs.game.common.netty.Global;
import com.qs.game.config.game.GameManager;
import com.qs.game.core.IThreadService;
import com.qs.game.model.base.ReqEntity;
import com.qs.game.model.base.RespEntity;
import com.qs.game.model.game.GoldDto;
import com.qs.game.model.game.Kuns;
import com.qs.game.model.game.Pool;
import com.qs.game.model.game.PoolCell;
import com.qs.game.core.ICommonService;
import com.qs.game.core.ILoginCMDService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by zun.wei on 2018/9/6 13:49.
 * Description: 登录命令业务接口实现类
 */
@Slf4j
@CommandService(CMD.LOGIN)
public class LoginCMDService implements ILoginCMDService {

    @Autowired
    private Global global;

    @Resource
    private ICommonService commonService;

    @Override
    public Runnable execute(ChannelHandlerContext ctx, TextWebSocketFrame msg, ReqEntity reqEntity) {
        //Future future =
        IThreadService.executor.execute(() -> {
            Integer cmd = reqEntity.getCmd();
            String mid = this.getPlayerId(ctx); //管道中的用户mid
            //获取玩家鲲池
            List<PoolCell> poolCells = this.getPlayerKunPoolCells(mid);
            long nowTime = new Date().getTime() / 1000;
            //获取离线这段时间产生的金币，并累加原有金币
            GoldDto goldDto = commonService.getPeriodTimeAndSrcGold(mid, poolCells, nowTime);
            //重设鲲池单元格中在工作的鲲的工作时间为当前时间
            poolCells = commonService.getPoolCellsAndResetWorkTime(poolCells, nowTime);

            Map<String, Object> content = new HashMap<>();
            content.put("pool", poolCells); //玩家鲲池
            content.put("num", GameManager.POOL_CELL_NUM); //鲲池单元格个数
            content.put("gold", goldDto.getNewGold()); //玩家最新的金币
            content.put("outOnlineGold", goldDto.getAddGold());//玩家离线的时候产生的金币
            //content.put("goldSpeed", this.getPlayerGoldSpeedByMid(mid)); //玩家产金币速度
            String resultStr = RespEntity.getBuilder().setCmd(cmd).setErr(ERREnum.SUCCESS)
                    .setContent(content).buildJsonStr();
            global.sendMsg2One(resultStr, mid);
            ReferenceCountUtil.release(msg);
        });
        /*try {
            Object o = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }*/
        //return () -> ReferenceCountUtil.release(msg);
        return null;
    }

    @Override
    public long getPlayerGoldSpeedByMid(String mid) {
        Pool pool = commonService.getPlayerKunPool(mid);
        return Objects.isNull(pool) ? 0L :
                pool.getPoolCells().stream()
                        .filter(e -> e.getKuns().getType() > 0 && e.getKuns().getWork() > 1)
                        .map(e -> KunGold.goldByType(e.getKuns().getType()))
                        .reduce((e1, e2) -> e1 + e2).orElse(0L);
    }


    @Override
    public long getPlayerGoldSpeedByKunPoolJson(String kunPoolJson) {
        return Optional.ofNullable(kunPoolJson).map(json -> {
            Map map = JSONObject.parseObject(json, Map.class);
            return JSONObject.parseArray(map.values().toString(), Kuns.class)
                    .stream()
                    //筛选出不是空的坑位 并且 正在工作的鲲
                    .filter(e -> e.getType() > 0 && e.getWork() > 0)
                    //把符合条件的鲲每秒产金币数合并统计
                    .map(e -> KunGold.goldByType(e.getType()))
                    .reduce((e1, e2) -> e1 + e2).orElse(0L);
        }).orElse(0L);
    }


    @Override
    public List<PoolCell> getPlayerKunPoolCells(String mid) {
        Pool pool = commonService.getPlayerKunPool(mid);
        return pool.getPoolCells();
    }

}
