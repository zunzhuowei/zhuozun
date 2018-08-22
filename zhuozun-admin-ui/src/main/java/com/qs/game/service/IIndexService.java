package com.qs.game.service;

import com.qs.game.api.entity.UserRequest;
import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.model.user.User;

/**
 * Created by zun.wei on 2018/8/19.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface IIndexService {

    BaseResult login(UserRequest userRequest);

    BaseResult register(User user);

}
