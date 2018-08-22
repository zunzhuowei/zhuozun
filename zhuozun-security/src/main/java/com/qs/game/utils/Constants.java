package com.qs.game.utils;

/**
 * Created by zun.wei on 2018/8/14 12:40.
 * Description: 常量
 */
public interface Constants {

    /**
     * 存储当前登录用户id的字段名
     */
    String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * token有效期（小时）
     */
    int TOKEN_EXPIRES_HOUR = 72;

    /**
     * 存放Token的header字段  (@author: rico)
     */
    String DEFAULT_TOKEN_NAME = "X-Token";

    /**
     * token 前缀
     */
    String TOKEN_PREFIX = "uToken:";

    String USER_NAME = "name";

    String PASSWORD = "password";

}
