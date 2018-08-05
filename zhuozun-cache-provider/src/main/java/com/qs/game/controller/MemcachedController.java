package com.qs.game.controller;

import com.alibaba.fastjson.JSON;
import com.qs.game.base.basecontroller.BaseController;
import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.enum0.Code;
import com.qs.game.model.product.GoodsBrand;
import com.qs.game.request.ProductRequest;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import static com.qs.game.cache.CacheKey.Memcached.PRODUCT_CENTER_GET_GOODS_BRAND_BY_ID;

/**
 * Created by zun.wei on 2018/8/5.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@RestController
@RequestMapping("/memcached/")
public class MemcachedController extends BaseController {


    @Resource
    private MemcachedClient memcachedClient;

    /**
     *  根据id 获取品牌信息
     * @param id user id
     * @return BaseResult
     */
    @GetMapping("get/goodsBrand/{id}")
    public BaseResult getGoodsBrandById(@PathVariable Long id)
            throws InterruptedException, MemcachedException, TimeoutException {
        String strObj = memcachedClient.get(PRODUCT_CENTER_GET_GOODS_BRAND_BY_ID.KEY + id);
        System.out.println("strObj = " + strObj);
        return BaseResult.getBuilder()
                .setCode(Code.ERROR_0)
                .setSuccess(true)
                .setContent(strObj)
                .build();
    }


    /**
     * 保存品牌到memcached中，json格式
     * @param productRequest productRequest
     * @return BaseResult
     */
    @PostMapping("set/goodsBrand")
    public BaseResult saveGoodsBrand(ProductRequest productRequest)
            throws InterruptedException, MemcachedException, TimeoutException {
        if (Objects.isNull(productRequest))
        return BaseResult.getBuilder().setCode(Code.ERROR_2).setSuccess(false)
                .setContent(null).build();
        Long id = productRequest.getGoodsBrand().getId();
        System.out.println("id = " + id);
        boolean b = memcachedClient.set(PRODUCT_CENTER_GET_GOODS_BRAND_BY_ID.KEY + id, 15 * 60 * 60 * 24, JSON.toJSONString(productRequest.getGoodsBrand()));
        return BaseResult.getBuilder().setCode(Code.ERROR_0).setSuccess(true)
                .setContent(productRequest).build();
    }


}
