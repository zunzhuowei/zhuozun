package com.qs.game.api.fallback;

import com.qs.game.api.RedisApi;
import com.qs.game.enum0.Code;
import com.qs.game.base.baseentity.BaseResult;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Created by zun.wei on 2018/8/4.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *
 * 客户端实现fallbackFactory对服务整体异常处理
 *
 */
@Component
public class RedisFallbackFactory implements FallbackFactory<RedisApi> {

    public RedisFallbackFactory() {
        System.out.println("1111111111111111111111111111111 = " + 99999999999999999L);
    }

    @Override
    public RedisApi create(Throwable throwable) {
        return new RedisApi() {
            @Override
            //@HystrixCommand(t)
            public BaseResult getUserById(Long id) {
                return BaseResult.getBuilder().setSuccess(false)
                        .setCode(Code.ERROR_200).setMessage("server busy").build();
            }

            @Override
            public BaseResult saveUserByJson(String userJson) {
                return BaseResult.getBuilder().setSuccess(false)
                        .setCode(Code.ERROR_200).setMessage("server busy").build();
            }
        };
    }

}
