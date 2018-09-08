package com.qs.game.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qs.game.business.BusinessThreadUtil;
import com.qs.game.cache.CacheKey;
import com.qs.game.common.CMD;
import com.qs.game.common.CommandService;
import com.qs.game.common.ERREnum;
import com.qs.game.common.Global;
import com.qs.game.config.GameManager;
import com.qs.game.handler.BusinessHandler;
import com.qs.game.model.base.ReqEntity;
import com.qs.game.model.base.RespEntity;
import com.qs.game.model.sys.Kun;
import com.qs.game.service.ILoginCMDService;
import com.qs.game.service.IRedisService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public Runnable execute(ChannelHandlerContext ctx, TextWebSocketFrame msg, ReqEntity reqEntity) {
        BusinessThreadUtil.getExecutor().submit(() -> {
            Integer cmd = reqEntity.getCmd();
            String mid = ctx.channel().attr(Global.attrUid).get(); //管道中的用户mid
            String kunKey = CacheKey.RedisPrefix.USER_KUN_POOL.KEY + mid;
            String kunCache = redisService.get(kunKey); //缓存中的鲲池
            //查看内存中是否已经保存了该玩家的鲲池数据下标
            Integer index = gameManager.getUserKunPoolPosition().get(Integer.valueOf(mid));
            if (Objects.nonNull(index)) {
                Map<Integer, Kun> kunMap = gameManager.getMemoryKunPool(mid, index);
                kunCache = JSONObject.toJSONString(kunMap);
            } else {
                if (StringUtils.isBlank(kunCache)) {
                    Map<Integer, Kun> kunMap = gameManager.getInitKunPool(); //初始化鲲池
                    kunCache = JSONObject.toJSONString(kunMap);
                    redisService.set(kunKey, kunCache);
                    //获取内存存储位置下标
                    gameManager.storageOnMemory(mid, kunMap);
                } else {
                    Map<Integer, Kun> kunMap = JSONObject.parseObject(kunCache, Map.class);
                    gameManager.storageOnMemory(mid, kunMap);
                }
            }
            Map<String, Object> content = new HashMap<>();
            content.put("pool", kunCache);
            content.put("allGold", 100001);
            String resultStr = RespEntity.getBuilder().setCmd(cmd).setErr(ERREnum.SUCCESS).setContent(content).buildJsonStr();
            global.sendMsg2One(resultStr, mid);
        });
        return null;
    }



}
