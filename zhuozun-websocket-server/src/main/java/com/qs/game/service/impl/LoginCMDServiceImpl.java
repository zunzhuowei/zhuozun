package com.qs.game.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qs.game.business.BusinessThreadUtil;
import com.qs.game.cache.CacheKey;
import com.qs.game.common.CMD;
import com.qs.game.common.CommandService;
import com.qs.game.common.ERREnum;
import com.qs.game.common.Global;
import com.qs.game.config.GameManager;
import com.qs.game.model.base.ReqEntity;
import com.qs.game.model.base.RespEntity;
import com.qs.game.model.game.UserKunGold;
import com.qs.game.model.sys.Kuns;
import com.qs.game.service.ILoginCMDService;
import com.qs.game.service.IRedisService;
import com.qs.game.service.IUserKunGoldService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by zun.wei on 2018/9/6 13:49.
 * Description: 登录命令业务接口实现类
 */
@Slf4j
@CommandService(CMD.LOGIN)
public class LoginCMDServiceImpl implements ILoginCMDService {

    @Autowired
    private IRedisService redisService;

    @Autowired
    private GameManager gameManager;

    @Autowired
    private Global global;

    @Resource
    private IUserKunGoldService userKunGoldService;

    @Override
    public Runnable execute(ChannelHandlerContext ctx, TextWebSocketFrame msg, ReqEntity reqEntity) {
        Future future = BusinessThreadUtil.getExecutor().submit(() -> {
            Integer cmd = reqEntity.getCmd();
            String mid = ctx.channel().attr(Global.attrUid).get(); //管道中的用户mid
            //获取玩家鲲池
            String kunCache = this.getPlayerKunPool(mid);
            Map<String, Object> content = new HashMap<>();
            content.put("pool", kunCache);
            content.put("gold", this.getPlayerGold(mid));
            content.put("goldSpeed", this.getPlayerGoldSpeedByKunPoolJson(kunCache));
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

    /*
    1.当前金币数量，
    2。每秒产生的金币，根据鲲池中的鲲在工作的鲲的类型以及数量计算。
     */

    @Override
    public long getPlayerGoldSpeedByMid(String mid) {
        String kunPoolJson = this.getPlayerKunPool(mid);
        return this.getPlayerGoldSpeedByKunPoolJson(kunPoolJson);
    }


    @Override
    public long getPlayerGoldSpeedByKunPoolJson(String kunPoolJson) {
        if (StringUtils.isBlank(kunPoolJson)) {
            return 0L;
        } else {
            Map map = JSONObject.parseObject(kunPoolJson, Map.class);
            return JSONObject.parseArray(map.values().toString(), Kuns.class)
                    .stream()
                    //筛选出不是空的坑位 并且 正在工作的鲲
                    .filter(e -> e.getType() > 0 && e.getWork() > 0)
                    //把符合条件的鲲每秒产金币数合并统计
                    .map(Kuns::getGold)
                    .reduce((e1, e2) -> e1 + e2).orElse(0L);
        }
    }

    @Override
    public long getPlayerGold(String mid) {
        String goldKey = CacheKey.RedisPrefix.USER_KUN_GOLD.KEY + mid;
        String goldStr = redisService.get(goldKey);
        if (Objects.isNull(goldStr)) {
            UserKunGold userKunGold = userKunGoldService.selectByPrimaryKey(Long.valueOf(mid));
            if (Objects.nonNull(userKunGold)) {
                Long gold = userKunGold.getGold();
                redisService.set(goldKey, gold);
                return gold;
            } else {
                return 0L;
            }
        } else {
            return Long.parseLong(goldStr);
        }
    }

    @Override
    public String getPlayerKunPool(String mid) {
        String kunPool = null;
        //查看内存中是否已经保存了该玩家的鲲池数据下标
        Integer index = gameManager.getUserKunPoolPosition().get(Integer.valueOf(mid));
        if (Objects.nonNull(index)) {
            //从内存中取出玩家鲲池信息
            Map<Integer, Kuns> kunMap = gameManager.getMemoryKunPool(mid, index);
            kunPool = JSONObject.toJSONString(kunMap);
        } else {
            String kunKey = CacheKey.RedisPrefix.USER_KUN_POOL.KEY + mid;
            //缓存中的鲲池
            kunPool = redisService.get(kunKey);
            if (StringUtils.isBlank(kunPool)) {
                //初始化鲲池
                Map<Integer, Kuns> kunMap = gameManager.getInitKunPool();
                kunPool = JSONObject.toJSONString(kunMap);
                redisService.set(kunKey, kunPool);
                //保存到内存中
                gameManager.storageOnMemory(mid, kunMap);
            } else {
                Map<Integer, Kuns> kunMap = JSONObject.parseObject(kunPool, Map.class);
                gameManager.storageOnMemory(mid, kunMap);
            }
        }
        return kunPool;
    }


}
