package com.qs.game.api;

import com.qs.game.api.fallback.MemcachedFallbackFactory;
import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.constant.ServiceName;
import com.qs.game.request.ProductRequest;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeoutException;

/**
 * Created by zun.wei on 2018/8/4.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@FeignClient(name = ServiceName.CACHE_PROVIDER, fallbackFactory = MemcachedFallbackFactory.class)//服务降级
public interface MemcachedApi {


    @GetMapping("/memcached/get/goodsBrand/{id}")
    BaseResult getGoodsBrandById(@PathVariable("id") Long id)
            throws InterruptedException, MemcachedException, TimeoutException;


    @PostMapping("/memcached/set/goodsBrand")
    BaseResult saveGoodsBrand(ProductRequest productRequest)
            throws InterruptedException, MemcachedException, TimeoutException;


}
