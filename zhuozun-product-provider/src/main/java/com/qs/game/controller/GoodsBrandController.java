package com.qs.game.controller;

import com.qs.game.api.MemcachedApi;
import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.enum0.Code;
import com.qs.game.model.product.GoodsBrand;
import com.qs.game.request.ProductRequest;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeoutException;

/**
 * Created by zun.wei on 2018/8/5.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@RestController
@RequestMapping("/goodsBrand/")
public class GoodsBrandController {


    @Resource
    private MemcachedApi memcachedApi;


    /**
     *  根据id 获取品牌信息
     * @param id user id
     * @return BaseResult
     */
    @GetMapping("get/{id}")
    public BaseResult getGoodsBrandById(@PathVariable Long id)
            throws InterruptedException, MemcachedException, TimeoutException {
        return memcachedApi.getGoodsBrandById(id);
    }


    /**
     * 保存品牌到memcached中，json格式
     *
     * @param productRequest productRequest
     * @return BaseResult
     */
    @PostMapping("set/one")
    public BaseResult saveGoodsBrand(@RequestBody ProductRequest productRequest)
            throws InterruptedException, MemcachedException, TimeoutException {
        return memcachedApi.saveGoodsBrand(productRequest);
    }



}
