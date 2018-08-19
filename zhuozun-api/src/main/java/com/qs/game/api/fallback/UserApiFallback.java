package com.qs.game.api.fallback;

import com.qs.game.api.UserApi;
import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.enum0.Code;
import com.qs.game.model.user.User;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Created by zun.wei on 2018/8/19.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Component
public class UserApiFallback implements FallbackFactory<UserApi> {


    @Override
    public UserApi create(Throwable throwable) {
        return new UserApi() {
            @Override
            public BaseResult add(User user) {
                return UserApiFallback.getBaseFailResult();
            }

            @Override
            public BaseResult del(Long id) {
                return UserApiFallback.getBaseFailResult();
            }

            @Override
            public BaseResult getUserById(Long id) {
                return UserApiFallback.getBaseFailResult();
            }
        };
    }


    private static BaseResult getBaseFailResult() {
        return BaseResult.getBuilder().setSuccess(false)
                .setMessage("user center not in service")
                .setCode(Code.ERROR_200).build();
    }

}
