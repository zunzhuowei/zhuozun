package com.qs.game.auth;

import com.qs.game.constant.StrConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zun.wei on 2018/8/14 13:23.
 * Description: TokenManager的默认实现
 */
@Component("defaultTokenManager")
public class DefaultTokenManager  implements TokenManager{

    /** 将token存储到JVM内存(ConcurrentHashMap)中   (@author: rico) */
    private static Map<String, String> tokenMap = new ConcurrentHashMap<>();

    /**
     * 利用UUID创建Token(用户登录时，创建Token)
     */
    @Override
    public String createToken(String username) {
        String token = UUID.randomUUID().toString().replaceAll(StrConst._STR, StrConst.EMPTY_STR);
        tokenMap.put(token, username);
        return token;
    }

    /**
     *  Token验证(用户登录验证)
     */
    @Override
    public boolean checkToken(String token) {
        return !StringUtils.isEmpty(token) && tokenMap.containsKey(token);
    }

    @Override
    public String checkTokenGetValue(String token) {
        return StringUtils.isBlank(token) ? null : tokenMap.get(token);
    }

    /**
     * Token删除(用户登出时，删除Token)
     */
    @Override
    public void deleteToken(String token) {
        tokenMap.remove(token);
    }

}
