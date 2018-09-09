package com.qs.game.common;

/**
 * 系统命令
 */
public enum CMD {

    CLOSE_SERVER(CmdValue.CLOSE_SERVER, "停服命令"),
    HAND_SHAKE(CmdValue.HAND_SHAKE, "连接握手命令"),
    LOGIN(CmdValue.LOGIN, "登录命令"),
    LOGOUT(CmdValue.LOGOUT, "登出命令"),
    MERGE(CmdValue.MERGE, "鲲合并命令"),
    ;

    public Integer VALUE; //命令值
    public String COMMENT; //解释

    CMD(Integer cmd, String comment) {
        this.VALUE = cmd;
        this.COMMENT = comment;
    }

    public Integer getVALUE() {
        return VALUE;
    }
}
