package com.qs.game.common.game;

/**
 * 系统命令
 */
public enum CMD {

    CLOSE_SERVER(CmdValue.CLOSE_SERVER, "停服命令"),
    HAND_SHAKE(CmdValue.HAND_SHAKE, "连接握手命令"),
    LOGIN(CmdValue.LOGIN, "登录命令"),
    LOGOUT(CmdValue.LOGOUT, "登出命令"),
    MERGE(CmdValue.MERGE, "鲲合并命令"),
    MOVE(CmdValue.MOVE, "鲲移动命令"),
    WORK(CmdValue.WORK, "把鲲放到海里"),
    UN_WORK(CmdValue.UN_WORK, "把鲲收回池里"),
    NEW(CmdValue.NEW, "创建一个新对象"),
    DELETE(CmdValue.DELETE, "删除一个对象"),
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
