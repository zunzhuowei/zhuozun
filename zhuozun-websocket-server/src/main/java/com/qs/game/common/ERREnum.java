package com.qs.game.common;

/**
 * 错误集合
 */
public enum ERREnum {

    SUCCESS(0, "success", "not error"),
    ILLEGAL_REQUEST_1(-1, "illegal_request", "校验空参数失败"),
    ILLEGAL_REQUEST_2(-2, "illegal_request", "服务器停服维护"),
    ILLEGAL_REQUEST_3(-3, "illegal_request", "鲲的类型不匹配"),
    ILLEGAL_REQUEST_4(-4, "illegal_request", "校验请求参数未通过"),
    ILLEGAL_REQUEST_5(-5, "illegal_request", "玩家鲲池对象为空"),
    ILLEGAL_REQUEST_6(-6, "illegal_request", "请求参数的鲲下标不在范围内"),

    SEAT_NOT_EMPTY(-100, "SEAT_NOT_EMPTY", "请求参数的鲲池下标不是一个空位置"),
    SEAT_IS_EMPTY(-101, "SEAT_IS_EMPTY", "请求参数的鲲池下标是一个空位置"),

    ;

    public Integer CODE;
    public String MSG;
    public String COMMENT;

    ERREnum(Integer code, String msg, String comment) {
        this.MSG = msg;
        this.COMMENT = comment;
        this.CODE = code;
    }

}
