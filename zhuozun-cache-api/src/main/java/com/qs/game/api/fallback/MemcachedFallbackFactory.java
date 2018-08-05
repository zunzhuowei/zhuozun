package com.qs.game.api.fallback;

import com.qs.game.api.MemcachedApi;
import com.qs.game.api.RedisApi;
import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.enum0.Code;
import com.qs.game.request.ProductRequest;
import feign.hystrix.FallbackFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeoutException;

/**
 * Created by zun.wei on 2018/8/4.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *
 * 客户端实现fallbackFactory对服务整体异常处理
 *
 */
@Component
public class MemcachedFallbackFactory implements FallbackFactory<MemcachedApi> {


    @Override
    public MemcachedApi create(Throwable throwable) {
        return new MemcachedApi() {
            @Override
            public BaseResult getGoodsBrandById(Long id) {
                return BaseResult.getBuilder().setSuccess(false)
                        .setCode(Code.ERROR_200).setMessage("server busy getGoodsBrandById").build();
            }

            @Override
            public BaseResult saveGoodsBrand(ProductRequest productRequest) {
                return BaseResult.getBuilder().setSuccess(false)
                        .setCode(Code.ERROR_200).setMessage("server busy saveGoodsBrand").build();
            }
        };
    }

}
