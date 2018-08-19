package com.qs.game.api;

import com.qs.game.api.fallback.UserApiFallback;
import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.constant.ServiceName;
import com.qs.game.model.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *  user center api
 */
@FeignClient(name = ServiceName.USER_CENTER_PROVIDER, fallbackFactory = UserApiFallback.class)//服务降级
public interface UserApi {

    @PostMapping("/user/add")
    BaseResult add(@RequestBody User user);


    @PostMapping("/user/del/{id}")
    BaseResult del(@PathVariable("id") Long id);

    @GetMapping("/user/get/{id}")
    BaseResult getUserById(@PathVariable("id") Long id);

}
