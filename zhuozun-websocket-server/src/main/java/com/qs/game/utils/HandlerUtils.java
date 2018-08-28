package com.qs.game.utils;


import com.alibaba.fastjson.JSONObject;
import com.qs.game.model.ReqEntity;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HandlerUtils {


    /**
     * 获取客户端短id
     */
    public static String getClientShortIdByChannel(Channel channel) {
        return channel.id().asShortText();
    }


    /**
     *  获取客户端长id
     */
    public static String getClientLongIdByChannel(Channel channel) {
        return channel.id().asLongText();
    }


    public static ReqEntity getReqEntity(String json) {
        log.info("HandlerUtils getReqEntity --::{}", json);
        ReqEntity reqEntity;
        try {
            reqEntity = JSONObject.parseObject(json, ReqEntity.class);
        } catch (Exception e) {
            reqEntity = new ReqEntity();
        }
        return reqEntity;
    }



}
