package com.qs.game.common;

/**
 * 错误集合
 */
public enum ERREnum {

    SUCCESS(0, "success", "not error"),
    ILLEGAL_REQUEST_1(-1, "illegal_request", "校验空参数失败"),
    ILLEGAL_REQUEST_2(-2, "illegal_request", "服务器停服维护"),
    ILLEGAL_REQUEST_3(-3, "illegal_request", "校验token过期时间失败"),
    ILLEGAL_REQUEST_4(-4, "illegal_request", "token已过期"),
    ILLEGAL_REQUEST_5(-5, "illegal_request", "token匹配失败"),
    ILLEGAL_REQUEST_6(-6, "illegal_request", "请求参数签名校验失败"),;

    public Integer CODE;
    public String MSG;
    public String COMMENT;

    ERREnum(Integer code, String msg, String comment) {
        this.MSG = msg;
        this.COMMENT = comment;
        this.CODE = code;
    }

}
