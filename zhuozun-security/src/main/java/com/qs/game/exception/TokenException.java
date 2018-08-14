package com.qs.game.exception;

/**
 * Created by zun.wei on 2018/8/14 11:40.
 * Description: 自定义的RuntimeException
 */
public class TokenException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String msg;

    public TokenException(String msg) {
        super();
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
