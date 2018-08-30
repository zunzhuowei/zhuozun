package com.qs.game.utils;

import com.alibaba.fastjson.JSONObject;
import com.qs.game.auth.JWTUtils;
import com.qs.game.enum0.DateEnum;
import com.qs.game.model.ReqEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 访问权限工具类
 */
@Slf4j
public class AccessUtils {


    //盐值
    private static final String SALT_VALUE = "2HC$OnKCGvgwKnOZ@mA4";
    private static final String SIGN = "sign";
    private static final String STAMP = "stamp";
    private static final String TOKEN = "token";
    private static final String CMD = "cmd";
    private static final String KEY = "key";
    private static final String EQUALS = "=";
    private static final String AND = "&";
    private static final char charr[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890$@#_".toCharArray();


    /**
     * 如果签名错误，或者不满足条件则返回空。否则发返回 ReqEntity
     *
     * @param requestJson 请求字符串
     * @return ReqEntity 实体类
     */
    public static ReqEntity checkAngGetReqEntity(String requestJson) {
        ReqEntity reqEntity = AccessUtils.getReqEntity(requestJson);
        Integer cmd = reqEntity.getCmd();
        //校验命令是否存在
        SysCmd sysCmd = Arrays.stream(SysCmd.values())
                .filter(e -> Objects.equals(e.CMD, cmd))
                .findFirst().orElse(null);
        log.info("AccessUtils checkReqEntity sysCmd --::{}", sysCmd);
        String sign = reqEntity.getSign();
        Long stamp = reqEntity.getStamp();
        String token = reqEntity.getToken();
        Map<String, Object> reqMap = reqEntity.getParams();
        //校验空参数
        boolean isBadReq = Objects.isNull(sysCmd)
                || Objects.isNull(sign)
                || Objects.isNull(stamp)
                || Objects.isNull(token);
        if (isBadReq) return null;
        //TODO 校验token是否存在缓存中（登录的时候放入的缓存）
        //TODO 校验签名的盐值应该是从token中去出来，针对不同的人进行签名。

        //参数排序准备签名比较
        if (Objects.isNull(reqMap)) reqMap = new TreeMap<>();
        //reqMap.put(SIGN, sign);
        reqMap.put(STAMP, stamp);
        reqMap.put(TOKEN, token);
        reqMap.put(CMD, sysCmd.CMD);
        TreeMap<String, Object> treeMap = new TreeMap<>(reqMap);
        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<String, Object> kv : treeMap.entrySet()) {
            buffer.append(kv.getKey()).append(EQUALS).append(kv.getValue()).append(AND);
        }
        String reqStr = buffer.deleteCharAt(buffer.length() - 1)
                .append(KEY).append(EQUALS).append(SALT_VALUE).toString();
        String md5Code = MD5Utils.getMD5Code(reqStr);
        log.info("AccessUtils checkReqEntity reqStr:{}, sign:{}, md5Code:{}", reqStr, sign, md5Code);
        //签名校验
        if (!StringUtils.equals(md5Code, sign)) {
            log.warn("AccessUtils checkReqEntity sign valid fail !");
            return null;
        }

        //校验token失效时间(同时也验证了token有效性了)
        Date expDate = JWTUtils.getExpiresAt(token);
        if (Objects.isNull(expDate)) {
            log.warn("AccessUtils checkReqEntity expDate is null or token is invalid");
            return null;
        }
        long expTime = expDate.getTime();
        long nowTime = new Date().getTime();
        if (expTime < nowTime) {
            String expDateFormat = DateEnum.YYYY_MM_DD_HH_MM_SS.getDateFormat().format(expDate);
            log.warn("AccessUtils checkReqEntity expDate = {}", expDateFormat);
            return null;
        }

        return reqEntity;
    }

    /**
     * 根据请求json 获取请求实体类
     *
     * @param json 请求json
     * @return ReqEntity
     */
    private static ReqEntity getReqEntity(String json) {
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

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(AccessUtils.createPassWord(16));
        }
    }

}
