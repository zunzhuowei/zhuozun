package com.qs.game.controller;

import com.qs.game.base.basecontroller.BaseController;
import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.enum0.CacheType;
import com.qs.game.enum0.Code;
import com.qs.game.request.CacheRequest;
import com.qs.game.service.IMemcachedService;
import com.qs.game.service.IRedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

/**
 * Created by zun.wei on 2018/8/5.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@RestController
@RequestMapping("/cache/")
@Api(value = "/cache",tags = {"缓存中心控制器"})
public class CacheController extends BaseController {


    @Resource
    private IRedisService redisService;

    @Resource
    private IMemcachedService memcachedService;

    @ApiOperation(value="根据key设置字符串缓存值",notes="对象请求参数")
    @PostMapping("set/str")
    public BaseResult setStr(CacheRequest<String> cacheRequest)
            throws InterruptedException, MemcachedException, TimeoutException {
        CacheType cacheType = cacheRequest.getCacheType();
        if (Objects.isNull(cacheType))
            return BaseResult.getBuilder().setSuccess(false).setCode(Code.ERROR_3).setMessage("cache type is not choose").setContent(cacheRequest).build();
        switch (cacheType) {
            case REDIS:
            {
                boolean b = redisService.set(cacheRequest.getKey(), cacheRequest.getValue(), cacheRequest.getLive());
                if (b)
                    return BaseResult.getBuilder().setSuccess(true).setCode(Code.ERROR_0).setContent(cacheRequest).build();
                return BaseResult.getBuilder().setSuccess(false).setCode(Code.ERROR_1).setContent(cacheRequest).build();
            }
            case MEMCACHED:
            {
                boolean b = memcachedService.set(cacheRequest.getKey(), cacheRequest.getValue(), cacheRequest.getLive());
                if (b)
                    return BaseResult.getBuilder().setSuccess(true).setCode(Code.ERROR_0).setContent(cacheRequest).build();
                return BaseResult.getBuilder().setSuccess(false).setCode(Code.ERROR_1).setContent(cacheRequest).build();
            }
            default:
            {
                return BaseResult.getBuilder().setSuccess(false).setCode(Code.ERROR_2).setContent(cacheRequest).build();
            }
        }

    }

    @ApiOperation(value="根据key获取缓存值",notes="restful风格")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cacheType", value = "缓存类型", required = true, paramType = "path", dataType = "string"),
            @ApiImplicitParam(name = "key", value = "缓存key", required = true, paramType = "path", dataType = "string"),
    })
    @GetMapping("get/{cacheType}/{key}")
    public BaseResult getValueRest(@PathVariable("cacheType") String cacheType, @PathVariable("key") String key)
            throws InterruptedException, MemcachedException, TimeoutException {
        if (StringUtils.isBlank(key))
            return BaseResult.getBuilder().setSuccess(false).setCode(Code.ERROR_2).setMessage("key is null").setContent(key).build();
        CacheType cacheTypeEnum = null;
        boolean b = Arrays.stream(CacheType.values()).anyMatch(e -> StringUtils.equals(e.name(), cacheType));
        if (!b)
            return BaseResult.getBuilder().setSuccess(false).setCode(Code.ERROR_4).setMessage("cache type is error").setContent(key).build();
        try {
            cacheTypeEnum = CacheType.valueOf(cacheType);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            cacheTypeEnum = CacheType.REDIS;
        }

        switch (cacheTypeEnum) {
            case REDIS:
                {
                    String value = redisService.get(key);
                    return BaseResult.getBuilder().setSuccess(true).setCode(Code.ERROR_0).setContent(value).build();
                }
            case MEMCACHED:
            {
                String value = memcachedService.get(key);
                return BaseResult.getBuilder().setSuccess(true).setCode(Code.ERROR_0).setContent(value).build();
            }
            default:
                return BaseResult.getBuilder().setSuccess(false).setCode(Code.ERROR_4).setContent(key).build();
        }
    }

    @ApiOperation(value="根据key获取缓存值",notes="对象请求参数")
    @GetMapping("get/by/key")
    public BaseResult getValueByKey(CacheRequest<String> cacheRequest)
            throws InterruptedException, MemcachedException, TimeoutException {
        CacheType cacheType = cacheRequest.getCacheType();
        if (Objects.isNull(cacheType))
            return BaseResult.getBuilder().setSuccess(false).setCode(Code.ERROR_3).setMessage("cache type is not choose").setContent(cacheRequest).build();
        switch (cacheType) {
            case REDIS:
                {
                    String value = redisService.get(cacheRequest.getKey());
                    return BaseResult.getBuilder().setSuccess(true).setCode(Code.ERROR_0).setContent(value).build();
                }
            case MEMCACHED:
            {
                String value = memcachedService.get(cacheRequest.getKey());
                return BaseResult.getBuilder().setSuccess(true).setCode(Code.ERROR_0).setContent(value).build();
            }
            default:
                return BaseResult.getBuilder().setSuccess(false).setCode(Code.ERROR_2).setContent(cacheRequest.getKey()).build();
        }
    }

}
