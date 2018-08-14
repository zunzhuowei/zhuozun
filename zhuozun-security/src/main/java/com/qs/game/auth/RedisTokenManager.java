package com.qs.game.auth;

import com.qs.game.constant.StrConst;
import com.qs.game.service.IRedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by zun.wei on 2018/8/14 17:33.
 * Description: TokenManager的Redis实现
 */
@Component("redisTokenManager")
public class RedisTokenManager implements TokenManager{

    @Resource
    private IRedisService redisService;

    @Override
    public String createToken(String username) {
        String token = UUID.randomUUID().toString().replaceAll(StrConst._STR, StrConst.EMPTY_STR);
        redisService.set(token, username);
        return token;
    }

    @Override
    public boolean checkToken(String token) {
        return StringUtils.isNotBlank(redisService.get(token));
    }

    @Override
    public void deleteToken(String token) {
        redisService.del(token);
    }


}
