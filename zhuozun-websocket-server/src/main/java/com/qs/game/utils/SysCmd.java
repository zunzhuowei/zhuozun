package com.qs.game.utils;

/**
 *  系统命令
 */
public enum SysCmd {

    LOGIN(1000,"登录命令"),
    LOGOUT(1001,"登出命令"),
    ;

    public Integer CMD; //命令值
    public String COMMENT; //解释

    SysCmd(Integer cmd, String comment) {
        this.CMD = cmd;
        this.COMMENT = comment;
    }


}
