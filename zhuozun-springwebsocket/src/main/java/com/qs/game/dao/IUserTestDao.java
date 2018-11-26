package com.qs.game.dao;

import com.qs.game.model.communication.UserTest;

/**
 * Created by zun.wei on 2018/11/27.
 */
public interface IUserTestDao {


    void saveUser(UserTest user);

    UserTest findUserByUserName(String userName);

    void updateUser(UserTest user);

    void deleteUserById(Long id);
}
