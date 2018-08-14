package com.qs.game.auth;

/**
 * Created by zun.wei on 2018/8/14 13:22.
 * Description:REST 鉴权    登录用户的身份鉴权
 */
public interface TokenManager {

    String createToken(String username);

    boolean checkToken(String token);

    void deleteToken(String token);

}
