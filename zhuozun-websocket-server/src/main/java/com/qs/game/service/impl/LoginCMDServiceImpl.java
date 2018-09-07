package com.qs.game.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qs.game.cache.CacheKey;
import com.qs.game.common.CMD;
import com.qs.game.common.CommandService;
import com.qs.game.common.ERREnum;
import com.qs.game.common.Global;
import com.qs.game.config.MemoryData;
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

    @Override
    public Runnable execute(ChannelHandlerContext ctx, TextWebSocketFrame msg, ReqEntity reqEntity) {
        Integer cmd = reqEntity.getCmd();
        String mid = ctx.channel().attr(Global.attrUid).get(); //管道中的用户mid
        String kunKey = CacheKey.RedisPrefix.USER_KUN_POOL.KEY + mid;
        String kunCache = redisService.get(kunKey); //缓存中的鲲池
        Integer index = MemoryData.getUserKunPoolPosition().get(Integer.valueOf(mid));
        if (Objects.nonNull(index)) {
            Map<Integer, Map<Integer, Kun>> storage = MemoryData.getKunStorageByIndex(index);
            Map<Integer, Kun> kunMap = storage.get(Integer.valueOf(mid));
            kunCache = JSONObject.toJSONString(kunMap);
        }else {
            if (StringUtils.isBlank(kunCache)) {
                Map<Integer, Kun> kunMap = MemoryData.getInitUserKunMap(); //初始化鲲池
                kunCache = JSONObject.toJSONString(kunMap);
                redisService.set(kunKey, kunCache);
                //获取内存存储位置下标
                this.storageOnMemory(mid, kunMap);
            } else {
                Map<Integer, Kun> kunMap = JSONObject.parseObject(kunCache, Map.class);
                this.storageOnMemory(mid, kunMap);
            }
        }
        Map<String, Object> content = new HashMap<>();
        content.put("pool", kunCache);
        content.put("allGold", 100001);
        String resultStr = RespEntity.getBuilder().setCmd(cmd).setErr(ERREnum.SUCCESS).setContent(content).buildJsonStr();
        return () -> ctx.writeAndFlush(new TextWebSocketFrame(resultStr));
    }

    //保存到内存中
    private void storageOnMemory(String mid, Map<Integer, Kun> kunMap) {
        //获取内存存储位置下标
        Integer index = MemoryData.getStorageIndex();
        //设置用户对应的存储下标
        MemoryData.getUserKunPoolPosition().put(Integer.valueOf(mid), index);
        //获取存储单元
        Map<Integer, Map<Integer, Kun>> storage = MemoryData.getKunStorageByIndex(index);
        //存储单元中存储玩家的鲲池
        storage.put(Integer.valueOf(mid), kunMap);
    }


}
