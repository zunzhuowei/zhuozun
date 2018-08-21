package com.qs.game.service;

import com.qs.game.model.user.User;
import com.qs.game.base.baseservice.IBaseService;

/**
 * Created by zun.wei on 2018/8/3.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface IUserService extends IBaseService<User, Long> {


    User queryBeanByUserName(String username);


}
