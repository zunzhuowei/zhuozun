package com.qs.game.swagger;

/**
 * Created by zun.wei on 2018/8/19.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *
 *  api definition
 *
 */
public enum Api {

    USER_API("/user-api","用户系统"),
    PRODUCT_API("/product-api","产品系统"),
    KAFKA_API("/kafka-api","消息系统"),
    CACHE_API("/cache-api","缓存系统"),
    ;

    public String API; // zuul.routes.*.path

    public String NAME;

    Api(String API, String NAME) {
        this.API = API;
        this.NAME = NAME;
    }

}
