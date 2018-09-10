package com.qs.game.common;

/**
 * Created by zun.wei on 2018/9/5 14:17.
 * Description: 命令常量值
 */
public interface CmdValue {

    int CLOSE_SERVER = 998; //停服
    int HAND_SHAKE = 999; //连接握手命令
    int LOGIN = 1000; //登录命令,
    int LOGOUT = 1001; //登出命令
    int MERGE = 1002; //鲲合并命令
    int NEW = 1003; //新建对象
    int DELETE = 1004;//删除对象
    int WORK = 1005; //把鲲放到海里
    int UN_WORK = 1006; //把鲲收回池里
    int MOVE = 1007; //鲲移动命令


}
