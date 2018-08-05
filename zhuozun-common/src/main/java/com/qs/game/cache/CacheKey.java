package com.qs.game.cache;

/**
 * Created by zun.wei on 2018/8/4.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface CacheKey {

    String getUserById = "getUserById:";
    String getGoodsBrandById = "getGoodsBrandById:";

    enum Redis{
        USER_CENTER_GET_USER_BY_ID(getUserById, "用户中心根据id获取用户"),
        ;

        public String KEY;
        public String COMMENT;
        Redis(String key,String comment) {
            this.KEY = key;
            this.COMMENT = comment;
        }
    }

    enum Memcached{
        USER_CENTER_GET_USER_BY_ID(getUserById, "用户中心根据id获取用户"),
        PRODUCT_CENTER_GET_GOODS_BRAND_BY_ID(getGoodsBrandById, "产品中心根据id获取品牌"),
        ;

        public String KEY;
        public String COMMENT;
        Memcached(String key,String comment) {
            this.KEY = key;
            this.COMMENT = comment;
        }
    }


}
