package com.qs.game.api;

import com.qs.game.api.fallback.RedisFallbackFactory;
import com.qs.game.constant.ServiceName;
import com.qs.game.model.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by zun.wei on 2018/8/4.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@FeignClient(name = ServiceName.CACHE_PROVIDER, fallbackFactory = RedisFallbackFactory.class)//服务降级
public interface RedisApi {

    @GetMapping("/redis/get/user/{id}")
    BaseResult getUserById(@PathVariable("id") Long id);

    @PostMapping("/redis/set/user/json")
    BaseResult saveUserByJson(@RequestParam(name = "userJson") String userJson);
}
