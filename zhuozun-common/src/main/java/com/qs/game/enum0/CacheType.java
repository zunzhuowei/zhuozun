package com.qs.game.enum0;

/**
 * Created by zun.wei on 2018/8/6.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 * 缓存类型
 */
public enum CacheType {

    REDIS("redis"), MEMCACHED("memcached"),ALL("all");

    public String name;

    CacheType(String name) {
        this.name = name;
    }

}
