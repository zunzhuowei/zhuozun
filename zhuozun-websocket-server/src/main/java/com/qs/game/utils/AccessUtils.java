package com.qs.game.utils;

import com.alibaba.fastjson.JSONObject;
import com.qs.game.auth.JWTEntity;
import com.qs.game.auth.JWTUtils;
import com.qs.game.cache.CacheKey;
import com.qs.game.common.CMD;
import com.qs.game.common.ERREnum;
import com.qs.game.enum0.DateEnum;
import com.qs.game.model.base.ReqEntity;
import com.qs.game.model.base.ReqErrEntity;
import com.qs.game.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 访问权限工具类
 */
@Slf4j
public class AccessUtils {


    //盐值
    //private static final String SALT_VALUE = "2HC$OnKCGvgwKnOZ@mA4";
    private static final String SIGN = "sign";
    private static final String STAMP = "stamp";
    private static final String TOKEN = "token";
    private static final String SYS_CMD = "cmd";
    private static final String KEY = "key";
    private static final String EQUALS = "=";
    private static final String AND = "&";
    private static final char charr[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890$@#_".toCharArray();

    //所有命令集合
    private static Set<Integer> cmdSet = null;

    /**
     * 如果签名错误，或者不满足条件则返回空。否则发返回 ReqEntity
     *
     * @param requestJson 请求字符串
     * @return ReqEntity 实体类
     */
    public static ReqErrEntity checkAndGetReqEntity(String requestJson) {
        ReqEntity reqEntity = AccessUtils.getReqEntity(requestJson);
        Integer reqCmd = reqEntity.getCmd();
        if (Objects.isNull(cmdSet))
            cmdSet = Arrays.stream(CMD.values()).map(CMD::getVALUE).collect(Collectors.toSet());
        //校验命令是否存在
        boolean isContain = cmdSet.contains(reqCmd);
        log.debug("AccessUtils checkReqEntity isContain cmd --::{}", isContain);
        //校验空参数
        if (!isContain){
            return new ReqErrEntity(ERREnum.ILLEGAL_REQUEST_1, reqEntity);
        }
        return new ReqErrEntity(ERREnum.SUCCESS, reqEntity);
    }

    /**
     * 根据请求json 获取请求实体类
     *
     * @param json 请求json
     * @return ReqEntity
     */
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


    /**
     * @param len 获取的随机密码长度
     * @return String 密码
     */
    public static String createPassWord(int len) {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int x = 0; x < len; ++x) {
            sb.append(charr[r.nextInt(charr.length)]);
        }
        return sb.toString();
    }

}
