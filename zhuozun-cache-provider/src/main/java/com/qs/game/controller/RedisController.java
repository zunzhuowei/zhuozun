package com.qs.game.controller;

import com.alibaba.fastjson.JSON;
import com.qs.game.base.basecontroller.BaseController;
import com.qs.game.cache.CacheKey;
import com.qs.game.constant.IntConst;
import com.qs.game.enum0.Code;
import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.service.impl.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zun.wei on 2018/8/4.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@RestController
@RequestMapping("/redis/")
public class RedisController extends BaseController {


    @Resource
    private RedisService redisService;

    //@Resource
    //private RedisTemplate<String,Object> redisTemplate;

    /**
     *  根据id 获取用户信息
     * @param id user id
     * @return BaseResult
     */
    @GetMapping("get/user/{id}")
    public BaseResult getUserById(@PathVariable Long id) {
        String userJson = redisService.get(CacheKey.Redis.USER_CENTER_GET_USER_BY_ID.KEY + id);
        //Object userJson = redisTemplate.opsForValue().get(CacheKey.Redis.USER_CENTER_GET_USER_BY_ID.KEY + id);
        System.out.println("userJson = " + userJson);
        return BaseResult.getBuilder()
                .setCode(Code.ERROR_0)
                .setSuccess(true)
                .setContent(userJson)
                .build();
    }


    /**
     * 保存用户到redis中，json格式
     * @param userJson user json text
     * @return BaseResult
     */
    @PostMapping("set/user/json")
    public BaseResult saveUserByJson(@RequestParam(name = "userJson") String userJson) {
        Map<String,Object> jsonMap = null;
        try {
            jsonMap = JSON.parseObject(userJson,Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.getBuilder().setCode(Code.ERROR_2).setSuccess(false)
                    .setContent(userJson).build();
        }
        String id = jsonMap.get("id") + "";
        if (StringUtils.isBlank(id))
            return BaseResult.getBuilder().setCode(Code.ERROR_1).setSuccess(false)
                    .setContent(userJson).build();
        redisService.set(CacheKey.Redis.USER_CENTER_GET_USER_BY_ID.KEY + id
                , userJson, IntConst.MONTH);//十五天
        //redisTemplate.opsForValue().set(CacheKey.Redis.USER_CENTER_GET_USER_BY_ID.KEY + id
        //        , userJson, 15,TimeUnit.DAYS);//十五天
        return BaseResult.getBuilder().setCode(Code.ERROR_0).setSuccess(true)
                .setContent(userJson).build();
    }

}
