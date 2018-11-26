package com.qs.game.dao;

import com.qs.game.model.communication.UserTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zun.wei on 2018/11/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {


    @Autowired
    private IUserTestDao userTestDao;

    @Test
    public void testSaveUser() throws Exception {
        UserTest user = new UserTest();
        user.setId(2l);
        user.setUserName("小明");
        user.setPassWord("fffooo123");
        userTestDao.saveUser(user);
    }

    @Test
    public void findUserByUserName() {
        UserTest user = userTestDao.findUserByUserName("小明");
        System.out.println("user is " + user);
    }

    @Test
    public void updateUser() {
        UserTest user = new UserTest();
        user.setId(2l);
        user.setUserName("天空");
        user.setPassWord("fffxxxx");
        userTestDao.updateUser(user);
    }

    @Test
    public void deleteUserById() {
        userTestDao.deleteUserById(1l);
    }

}
